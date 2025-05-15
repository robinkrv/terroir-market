package fr.ecommerce.responses;

public class UserResponseDTO {
    private Long id;
    private String firstname;
    private String name;
    private String username;
    private String email;
    private String accountStatus;

    public UserResponseDTO(Long id, String firstname, String name, String username, String email, String accountStatus) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.username = username;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
