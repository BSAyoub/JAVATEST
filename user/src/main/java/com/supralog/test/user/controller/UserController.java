package com.supralog.test.user.controller;


import com.supralog.test.user.entity.User;
import com.supralog.test.user.exception.UserNotFoundException;
import com.supralog.test.user.repository.UserRepository;
import com.supralog.test.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

//RestController  combination of  @Controller and @ResponseBody
@RestController
@RequestMapping("/users")
@Validated //trigger validation for the input data
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    //get localhost:8080/users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // service to display the details of an registered user by using its username
    //exemple : http://localhost:8080/users/a.bousaid
    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserDetails(@PathVariable String userName) {
        try {
            // Return user details
            User user = userService.findByUsername(userName);
            return ResponseEntity.ok(user);

        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    //post
    //register a user
    @PostMapping(value ="/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            //return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
            return ResponseEntity.ok("Registration successful");
        } catch (DuplicateKeyException e) {
            // email already exists
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists. Please use a different email.");
        } catch (IllegalArgumentException ex) {
            // Handle the IllegalArgumentException here
            String errorMessage = "Validation failed: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    // service to display the details of an registered user by using its firstname and lastname
    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<?> getUserDetailsByFirstAndLat(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        // Return user details
        User user = userService.findByFirstandLast(firstName,lastName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // service to display the details of an registered user by using its email
    //exemple http://localhost:8080/users/email/j.atta@supralog.fr
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserDetailsByEmail(@PathVariable("email") String email) {
        // Return user details
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //put
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        // Update the user using the UserService
        try {
            User updatedUserResult = userService.updateUserByUsername(username, updatedUser);
            if (updatedUserResult == null) {
                // Handle the case where the user does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User with username " + username + " not found.");
            }
            // Return an OK response with the updated user
            return ResponseEntity.status(HttpStatus.OK).body(updatedUserResult);
        }
        catch ( IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

    }

}
