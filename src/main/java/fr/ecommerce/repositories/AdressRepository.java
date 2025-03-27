package fr.ecommerce.repositories;

import fr.ecommerce.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<Address,Long> {
}
