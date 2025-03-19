package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProducerRepository extends JpaRepository<Producer,Long> {
}
