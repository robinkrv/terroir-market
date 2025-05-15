package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
}
