package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    @Query("""
    SELECT u FROM User u 
    LEFT JOIN FETCH u.userRoles ur 
    LEFT JOIN FETCH ur.role r 
    LEFT JOIN FETCH r.rolePermissions rp 
    WHERE u.username = :username OR u.email = :email
""")
    Optional<User> findByUsernameOrEmailWithRoles(String username, String email);
}
