package fr.ecommerce.controllers;

import fr.ecommerce.responses.ResponseDTO;
import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Admin controller.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')") // Limite cet endpoint au rôle ADMIN
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')") // Exige la permission DELETE_USER
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseDTO<Void> response = ResponseDTO.success("L'utilisateur a été supprimé avec succès.", null);
        return ResponseEntity.ok(response);
    }
}

