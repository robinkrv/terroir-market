package fr.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(

        @NotBlank(message = "Le prénom est requis.")
        @Size(min = 1, max = 100, message = "Le prénom doit contenir entre 1 et 100 caractères.")
        String firstname,

        @NotBlank(message = "Le nom est requis.")
        @Size(min = 1, max = 100, message = "Le nom doit contenir entre 1 et 100 caractères.")
        String name,

        @NotBlank(message = "Le nom d'utilisateur est requis.")
        @Size(min = 3, max = 100, message = "Le nom d'utilisateur doit contenir entre 3 et 100 caractères.")
        String username,

        @NotBlank(message = "L'email est requis.")
        @Email(message = "L'email n'est pas valide.")
        @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères.")
        String email,

        @NotBlank(message = "Le mot de passe est requis.")
        @Size(min = 6, max = 100, message = "Le mot de passe doit contenir entre 6 et 100 caractères.")
        String password,

        @Size(max = 20, message = "Le numéro de téléphone ne doit pas dépasser 20 caractères.")
        String phoneNumber,
        String accountStatus
) {}

