package com.supralog.test.user;

import com.supralog.test.user.entity.Address;
import com.supralog.test.user.entity.User;
import com.supralog.test.user.repository.UserRepository;
import com.supralog.test.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUser_SuccessfulRegistration() {
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_UnderageUser() {
        User user = createUser();
        user.setAge(17); // Underage
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUser_NonFrenchUser() {
        User user = createUser();
        user.getAddress().setCountry("Germany"); // Non-French country
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void testUpdateUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        User result = userService.updateUserByUsername("nonexistent", createUser());

        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
        verify(userRepository, never()).save(any(User.class));
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Bernard");
        user.setUsername("johnbernard");
        user.setPassword("password123");
        user.setEmail("johnbernard@supralog.fr");
        user.setAge(25);
        Address address = new Address();
        address.setCountry("France");
        user.setAddress(address);
        return user;
    }
}
