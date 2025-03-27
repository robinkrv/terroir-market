package fr.ecommerce.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.ecommerce.constants.AccountStatus;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @NotBlank(message = "Veuillez renseigner votre prénom")
    @Size(min = 1, max = 100, message = "Ce prénom ne peut pas être enregistré")
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "Veuillez renseigner votre nom")
    @Size(min = 1, max = 100, message = "Ce nom ne peut pas être enregistré")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Un nom d'utilisateur est requis")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 50 caractères.")
    @Column(name = "username")
    private String username;

    @Email
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 3, max = 20, message = "Le mot de passe doit contenir entre 3 et 20 caractères.")
    @Column(name = "password")
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> adresses;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketingConsent> marketingConsents;

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public List<Address> getAdresses() { return adresses; }
    public void setAdresses(List<Address> adresses) { this.adresses = adresses; }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<MarketingConsent> getMarketingConsents() { return marketingConsents; }
    public void setMarketingConsents(List<MarketingConsent> marketingConsents) { this.marketingConsents = marketingConsents; }
}
