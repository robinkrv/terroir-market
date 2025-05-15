package fr.ecommerce.controllers;

import fr.ecommerce.dto.*;
import fr.ecommerce.responses.ResponseDTO;
import fr.ecommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register response entity.
     *
     * @param registerDTO the register dto
     * @return the response entity
     */
// Créer un utilisateur (Register)
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            UserDTO createdUser = userService.createUser(registerDTO); // Crée l'utilisateur
            // Utilise la méthode statique 'success' pour créer la réponse
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDTO.success("Utilisateur créé avec succès", createdUser));
        } catch (RuntimeException e) {
            // En cas d'erreur, utilise 'error' pour la réponse
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDTO.error(e.getMessage()));
        }
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
// Exemple d'une autre méthode : Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(ResponseDTO.success("Utilisateur récupéré", userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO.error("Utilisateur non trouvé"));
        }
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        // Retourne la liste des utilisateurs récupérée dans le service
        return userService.getAllUsers();
    }

    /**
     * Update user response entity.
     *
     * @param id            the id
     * @param userUpdateDTO the user update dto
     * @return the response entity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUserDTO);
    }

    /**
     * Update password response entity.
     *
     * @param dto       the dto
     * @param principal the principal
     * @return the response entity
     */
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO dto, Principal principal) {
        // On récupère l'email de l'utilisateur courant à partir du token (ou Principal)
        String email = principal.getName();

        // Mise à jour du mot de passe
        userService.updatePassword(email, dto);

        return ResponseEntity.ok("Mot de passe mis à jour avec succès !");
    }

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseDTO<Void> response = ResponseDTO.success("L'utilisateur a été supprimé avec succès.", null);
        return ResponseEntity.ok(response);
    }
}

