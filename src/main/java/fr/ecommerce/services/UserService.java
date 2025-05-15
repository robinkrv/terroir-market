package fr.ecommerce.services;

import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.dto.UpdatePasswordDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.dto.UserUpdateDTO;
import fr.ecommerce.exceptions.EmailOrUsernameAlreadyExistsException;
import fr.ecommerce.exceptions.UserNotFoundException;
import fr.ecommerce.mappers.UserMapper;
import fr.ecommerce.models.entities.User;
import fr.ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        System.out.println("PasswordEncoder utilisé : " + passwordEncoder.getClass().getName());
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create user user dto.
     *
     * @param registerDTO the register dto
     * @return the user dto
     */
    public UserDTO createUser(RegisterDTO registerDTO) {
        try {
            checkEmailAndUsernameUniqueness(registerDTO.email(), registerDTO.username());
        } catch (EmailOrUsernameAlreadyExistsException e) {
            throw new RuntimeException("L'email ou le nom d'utilisateur est déjà pris");
        }
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

    /**
     * Gets current user id.
     *
     * @return the current user id
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal(); // Caster en ton entité User
            return user.getId(); // Récupérer directement l'ID
        }
        throw new SecurityException("Utilisateur non authentifié");
    }

    /**
     * Update user user dto.
     *
     * @param id            the id
     * @param userUpdateDTO the user update dto
     * @return the user dto
     */
    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        userMapper.updateUserFromDto(userUpdateDTO, user);

        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
    }

    /**
     * Update password.
     *
     * @param email the email
     * @param dto   the dto
     */
    public void updatePassword(String email, UpdatePasswordDTO dto) {
        // Récupération de l'utilisateur depuis la base
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        // Vérification du mot de passe actuel
        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Le mot de passe actuel est incorrect.");
        }

        // Encodage du nouveau mot de passe
        String encodedNewPassword = passwordEncoder.encode(dto.newPassword());

        // Mise à jour de l'utilisateur
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
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

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        // Conversion vers DTO avant le retour
        return userMapper.toDTO(user);
    }

    /**
     * Find by username or email optional.
     *
     * @param usernameOrEmail the username or email
     * @return the optional
     */
// 🔹 Trouver par username/email
    public Optional<UserDTO> findByUsernameOrEmail(String usernameOrEmail) {
        // Recherche un utilisateur par username ou email
        Optional<User> user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));

        // Conversion en DTO si l'utilisateur est trouvé
        return user.map(userMapper::toDTO);
    }

    /**
     * Convert to dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
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

    /**
     * Delete user.
     *
     * @param id the id
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("L'utilisateur avec l'ID " + id + " est introuvable.");
        }
        userRepository.deleteById(id);
    }
}
