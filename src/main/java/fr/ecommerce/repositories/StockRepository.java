package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock,Long> {
}
