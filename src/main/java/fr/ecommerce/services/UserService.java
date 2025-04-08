package fr.ecommerce.services;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.models.entities.User;
import fr.ecommerce.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 Création avec RegisterDTO
    public UserDTO createUser(RegisterDTO registerDTO) {
        checkEmailAndUsernameUniqueness(registerDTO.email(), registerDTO.username());

        User user = new User();
        user.setFirstname(registerDTO.firstname());
        user.setName(registerDTO.name());
        user.setUsername(registerDTO.username());
        user.setEmail(registerDTO.email());
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        user.setAccountStatus(AccountStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // 🔹 Vérifie l'unicité de l'email et du username
    private void checkEmailAndUsernameUniqueness(String email, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris.");
        }
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return convertToDTO(user);
    }

    // 🔹 Trouver par username/email
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));
    }

    // 🔹 Convertir en DTO sans password
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAccountStatus(),
                null, // adresses plus tard
                null  // rôles plus tard
        );
    }
}
