package fr.ecommerce.repositories;

import fr.ecommerce.constants.UserRoleName;
import fr.ecommerce.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(UserRoleName name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.rolePermissions WHERE r.name = :roleName")
    Optional<Role> findByNameWithPermissions(@Param("roleName") UserRoleName roleName);
}

