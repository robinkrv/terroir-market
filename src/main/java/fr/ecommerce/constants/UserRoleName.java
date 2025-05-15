package fr.ecommerce.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRoleName {
    ADMIN,
    MANAGER,
    PRODUCER,
    USER,
    GUEST
}
