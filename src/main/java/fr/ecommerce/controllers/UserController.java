package fr.ecommerce.controllers;

import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.dto.ResponseDTO;
import fr.ecommerce.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Créer un utilisateur (Register)
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody RegisterDTO registerDTO) {
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
}
