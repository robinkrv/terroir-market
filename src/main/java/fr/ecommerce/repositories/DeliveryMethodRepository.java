package fr.ecommerce.repositories;


import fr.ecommerce.models.entities.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod,Long> {
}