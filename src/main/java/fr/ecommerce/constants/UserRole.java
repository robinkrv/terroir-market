package fr.ecommerce.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
    ADMIN,
    MANAGER,
    USER,
    GUEST
}
