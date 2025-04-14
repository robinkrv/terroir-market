package fr.ecommerce.services;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.dto.UserUpdateDTO;
import fr.ecommerce.exceptions.UserNotFoundException;
import fr.ecommerce.mappers.UserMapper;
import fr.ecommerce.models.entities.User;
import fr.ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDTO) // Utilisation du UserMapper pour convertir chaque User en UserDTO
                .collect(Collectors.toList());
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

    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        userMapper.updateUserFromDto(userUpdateDTO, user);

        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
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
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        // Conversion vers DTO avant le retour
        return userMapper.toDTO(user);
    }

    // 🔹 Trouver par username/email
    public Optional<UserDTO> findByUsernameOrEmail(String usernameOrEmail) {
        // Recherche un utilisateur par username ou email
        Optional<User> user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));

        // Conversion en DTO si l'utilisateur est trouvé
        return user.map(userMapper::toDTO);
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

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("L'utilisateur avec l'ID " + id + " est introuvable.");
        }
        userRepository.deleteById(id);
    }
}
