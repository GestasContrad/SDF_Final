package org.example.sdf_final.repository;

import org.example.sdf_final.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // Find User by email - error
    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(User.Role.STUDENT);

        userRepository.save(user);

        Optional<User> foundUser =
                userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com",
                foundUser.get().getEmail());
    }

    // Find user by email - success
    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {

        Optional<User> foundUser =
                userRepository.findByEmail("missing@example.com");

        assertTrue(foundUser.isEmpty());
    }
}
