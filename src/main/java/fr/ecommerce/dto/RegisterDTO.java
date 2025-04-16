package fr.ecommerce.dto;

public record RegisterDTO(
        String firstname,
        String name,
        String username,
        String email,
        String password,
        String phoneNumber,
        String accountStatus
) {}

