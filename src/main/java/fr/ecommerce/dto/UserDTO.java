package fr.ecommerce.dto;

import fr.ecommerce.constants.AccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserDTO {
    private Long id;

    @NotBlank(message = "Le prénom est requis.")
    @Size(min = 1, max = 100, message = "Le prénom doit contenir entre 1 et 100 caractères.")
    private String firstname;

    @NotBlank(message = "Le nom est requis.")
    @Size(min = 1, max = 100, message = "Le nom doit contenir entre 1 et 100 caractères.")
    private String name;

    @NotBlank(message = "Le nom d'utilisateur est requis.")
    @Size(min = 3, max = 100, message = "Le nom d'utilisateur doit contenir entre 3 et 100 caractères.")
    private String username;

    @NotBlank(message = "L'email est requis.")
    @Email(message = "L'email n'est pas valide.")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères.")
    private String email;

    @Size(max = 20, message = "Le numéro de téléphone ne doit pas dépasser 20 caractères.")
    private String phoneNumber;
    private AccountStatus accountStatus;
    private List<AddressDTO> addresses;
    private List<Long> rolesIds;


    // 🔥 Constructeur vide (pour Jackson / Spring)
    public UserDTO() {}

    // 🔥 Constructeur complet
    public UserDTO(Long id, String firstname, String name, String username, String email,
                   String phoneNumber, AccountStatus accountStatus,
                   List<AddressDTO> addresses, List<Long> rolesIds){
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountStatus = accountStatus;
        this.addresses = addresses;
        this.rolesIds = rolesIds;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }
}

