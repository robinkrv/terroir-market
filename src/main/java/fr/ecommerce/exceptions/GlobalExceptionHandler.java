package fr.ecommerce.exceptions;

import fr.ecommerce.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // ✅ Gestion des erreurs de validation
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDTO<String>> handleValidationExceptions(BindException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.badRequest().body(ResponseDTO.error(errorMessage.toString()));
    }

    // ✅ Gestion des erreurs "Utilisateur introuvable"
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.error(ex.getMessage()));
    }

    // ✅ Gestion des erreurs générales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        log.error("Erreur inattendue interceptée : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.error("Une erreur inattendue est survenue : " + ex.getClass().getName()));
    }
}
