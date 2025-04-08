package fr.ecommerce.dto;

public record LoginDTO(
        String usernameOrEmail,
        String password
) {}

