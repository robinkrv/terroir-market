package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ProducerRepository extends JpaRepository<Producer,Long> {

    @Query("SELECT p FROM Producer p LEFT JOIN FETCH p.products WHERE p.id = :id")
    Optional<Producer> findByIdWithProducts(@Param("id") Long id);

}
