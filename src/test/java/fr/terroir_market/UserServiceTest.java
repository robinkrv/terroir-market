package fr.terroir_market;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.mappers.UserMapper;
import fr.ecommerce.models.entities.User;
import fr.ecommerce.repositories.UserRepository;
import fr.ecommerce.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserMapper userMapper;

    @InjectMocks private UserService userService;

    @Test
    void testCreateUser_success() {
        // Given
        RegisterDTO registerDTO = new RegisterDTO(
                "john",
                "doe",
                "johndoe",
                "john@example.com",
                "password",
                "0123456789",
                "ACTIVE"
        );

        // Mock : email et username n'existent pas encore
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.empty());

        // Mock : encode le mot de passe
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Prépare l'utilisateur attendu
        User userToSave = new User();
        userToSave.setFirstname("john");
        userToSave.setName("doe");
        userToSave.setUsername("johndoe");
        userToSave.setEmail("john@example.com");
        userToSave.setPassword("encodedPassword");
        userToSave.setAccountStatus(AccountStatus.ACTIVE);

        // Simule le résultat du save()
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstname(userToSave.getFirstname());
        savedUser.setName(userToSave.getName());
        savedUser.setUsername(userToSave.getUsername());
        savedUser.setEmail(userToSave.getEmail());
        savedUser.setPassword(userToSave.getPassword());
        savedUser.setAccountStatus(userToSave.getAccountStatus());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Simule le mapping final
        UserDTO expectedDTO = new UserDTO(
                1L,
                "john",
                "doe",
                "johndoe",
                "john@example.com",
                null,
                AccountStatus.ACTIVE,
                null,
                null
        );

        // When
        UserDTO result = userService.createUser(registerDTO);

        // Then
        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        assertEquals("john@example.com", result.getEmail());

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }
}
