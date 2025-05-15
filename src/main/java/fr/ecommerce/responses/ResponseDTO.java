package fr.ecommerce.responses;

import java.time.Instant;

public class ResponseDTO<T> {
    private String status;
    private String message;
    private T data;
    private Instant timestamp;

    private ResponseDTO(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    public static <T> ResponseDTO<T> success(String message, T data) {
        return new ResponseDTO<>("success", message, data);
    }

    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>("error", message, null);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Instant getTimestamp() { return timestamp; }
}
