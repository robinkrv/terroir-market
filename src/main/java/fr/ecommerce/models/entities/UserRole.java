package fr.ecommerce.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "users_roles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}) // Garantit l'unicité
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clé primaire auto-générée
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE) // Active la suppression en cascade
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false) // Relie au rôle
    private Role role;

    @Override
    public String getAuthority() {
        return role.getName().name(); // Retourne "USER", "ADMIN", etc.
    }

    // Constructeur par défaut (obligatoire pour JPA)
    public UserRole() {}

    // Constructeur pour initialisation simplifiée
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // equals() et hashCode() basés sur l'identifiant auto-généré
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id != null && id.equals(userRole.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
