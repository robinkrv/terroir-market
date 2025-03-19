package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
