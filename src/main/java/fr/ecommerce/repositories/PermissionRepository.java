package fr.ecommerce.repositories;


import fr.ecommerce.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
