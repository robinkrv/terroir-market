package fr.ecommerce.models.entities;


import fr.ecommerce.models.identifiers.UserRoleId;
import jakarta.persistence.*;

@Entity
@Table(name = "users_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")  // Le champ userId est mappé sur la clé composite
    private User user;

    @ManyToOne
    @MapsId("roleId")  // Le champ roleId est mappé sur la clé composite
    private Role role;

    // Optionnel : Constructeur par défaut
    public UserRole() {}

    // Constructeur avec les arguments nécessaires
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId(user.getId(), role.getId()); // Création de la clé composite
    }

    // equals() et hashCode() basés sur les deux propriétés de la clé composite
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id.equals(userRole.id); // On compare la clé composite
    }

    @Override
    public int hashCode() {
        return id.hashCode(); // On utilise la méthode hashCode de UserRoleId
    }
}
