package fr.ecommerce.repositories;


import fr.ecommerce.models.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}