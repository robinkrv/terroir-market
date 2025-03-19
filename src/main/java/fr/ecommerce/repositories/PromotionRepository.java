package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
}
