package fr.ecommerce.models.entities;

import fr.ecommerce.models.identifiers.RolePermissionId;
import jakarta.persistence.*;

@Entity
@Table(name = "role_permissions")
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;  // Clé composite

    @ManyToOne
    @MapsId("roleId")  // Relie le rôle à l'ID de RolePermission
    private Role role;  // Utilisation de l'ID composite ici

    @ManyToOne
    @MapsId("permissionId")  // Relie la permission à l'ID de RolePermission
    private Permission permission;  // Utilisation de l'ID composite ici

    // Constructeur vide pour JPA
    public RolePermission() {}

    // Constructeur personnalisé pour initialiser l'entité
    public RolePermission(Role role, Permission permission) {
        this.id = new RolePermissionId(role.getId(), permission.getId());
        this.role = role;
        this.permission = permission;
    }

    // Getters et setters
    public RolePermissionId getId() {
        return id;
    }

    public void setId(RolePermissionId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
