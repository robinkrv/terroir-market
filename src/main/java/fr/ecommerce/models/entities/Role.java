package fr.ecommerce.models.entities;

import fr.ecommerce.constants.UserRoleName;
import fr.ecommerce.models.base.AbstractEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private UserRoleName name;

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;


    public UserRoleName getName() {
        return name;
    }

    public void setName(UserRoleName name) {
        this.name = name;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}

