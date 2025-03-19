package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
