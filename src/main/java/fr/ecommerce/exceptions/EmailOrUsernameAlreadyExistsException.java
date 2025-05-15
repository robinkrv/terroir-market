package fr.ecommerce.exceptions;

public class EmailOrUsernameAlreadyExistsException extends RuntimeException {
    public EmailOrUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
