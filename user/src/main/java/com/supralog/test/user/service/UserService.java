package com.supralog.test.user.service;
import com.supralog.test.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.supralog.test.user.entity.User;
import com.supralog.test.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * Finds a user by their username.
     *
     * This method retrieves a user from the repository based on the provided username.
     *
     * @param username The username of the user to search for.
     * @return The User object with the specified username, or null if no user with the
     *         given username is found in the repository.
     */
    public User findByUsername(String username) {
        User existinguser =userRepository.findByUsername(username);
        if(existinguser == null){
            logger.info("Info : the user [" +username +"] does not exist");
            throw new UserNotFoundException(username);
        }
        return  existinguser;

    }

    /**
     * Registers a new user.
     *
     * This method registers a new user by validating the provided 'user' object
     * and saving it to the data store if the user meets the required criteria.
     *
     * @param user The User object representing the new user to be registered.
     *             It should contain valid user information, non-null username,
     *             age greater than 18, and country = "France" to meet
     *             the registration condition.
     * @return The registered User object with additional information such as create date,
     *         or null if the registration criteria are not met or if a user with the same
     *         email already exists.
     * @throws IllegalArgumentException If the 'user' does not meet the registration condition.
     */
    public User registerUser(User user) {
        if (user.getAge() < 18 || !user.getAddress().getCountry().equalsIgnoreCase("France")) {
            logger.error("Error : Only adults (>18) living in France can create an account.");
            throw new IllegalArgumentException("Only adults (>18) living in France can create an account.");
        }
        if (user.getPassword().isEmpty()) {
            logger.error("Error : password can not be null.");
            throw new IllegalArgumentException("password can not be null, give a robust password");
        }
        return userRepository.save(user);
    }
    /**
     * Updates a user's information based on their username.
     *
     * This method retrieves an existing user by their username, and then updates their
     * information with the provided data in the 'updatedUser' parameter. The user's
     * information is updated in the data store.
     *
     * @param username    The username of the user to update.
     * @param updatedUser The User object containing the updated user information.
     *                    It should include the new values for the user's attributes.
     * @return The updated User object with the modified information.
     *         Returns null if the specified username does not exist in the data store.
     * @throws IllegalArgumentException If 'updatedUser'  if the username is null or empty.
     */
    public User updateUserByUsername(String username, User updatedUser) {
        // Find the user by username
        User existingUser = userRepository.findByUsername(username);

        if (existingUser == null) {
            logger.error("Error : username [" + username + "] does not exist");
            throw new IllegalArgumentException("Error : username [" + username + "] does not exist");
        }
        if(updatedUser.getAge()< 18 || !updatedUser.getAddress().getCountry().equalsIgnoreCase("France")){
            logger.error("Error : username [" + username + "] cant have its age or country modified");
            throw new IllegalArgumentException("Error : username [" + username + "] cant have its age or country modified");
        }

        // Update the user's information
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPassword(updatedUser.getPassword());

        //here I should add a possibility of modifiying the email but it should not exist for another user
        /*
        *
        * */
        User existingUserEmail = userRepository.findByEmail(updatedUser.getEmail());
        if(existingUserEmail != null &&  !existingUserEmail.getUsername().equals(username)){
            logger.error("Error : username [" + username + "] cant have this email cuz it belongs to another user");
            throw new IllegalArgumentException("Error : username [" + username + "] cant have this email cuz it belongs to another user");
        }
        else if (!updatedUser.getEmail().equals(existingUser.getEmail())){
            existingUser.setEmail(updatedUser.getEmail());
        }
        // Save the updated user back to the database mongodb
        return userRepository.save(existingUser);
    }

    public List<User> findAll() {
        return  userRepository.findAll();
    }


    public User findByFirstandLast(String firstName, String lastName){
        return  userRepository.findByFirstNameAndLastName(firstName,lastName);
    }
    public User getUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }
}
