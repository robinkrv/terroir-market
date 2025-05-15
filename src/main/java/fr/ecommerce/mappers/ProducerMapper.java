package fr.ecommerce.mappers;

import fr.ecommerce.dto.ProducerCreateDTO;
import fr.ecommerce.dto.ProducerUpdateDTO;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.models.entities.Product;
import fr.ecommerce.models.entities.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The interface Producer mapper.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProducerMapper {

    /**
     * The constant INSTANCE.
     */
// Instance pour les tests indépendants de Spring si besoin
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    /**
     * To entity producer.
     *
     * @param dto the dto
     * @return the producer
     */
    @Mapping(target = "products", expression = "java(dto.getProductIds() == null ? new java.util.ArrayList<>() : null)")
    Producer toEntity(ProducerCreateDTO dto);

    /**
     * To dto producer create dto.
     *
     * @param producer the producer
     * @return the producer create dto
     */
    @Mapping(target = "productIds", source = "products") // Mapper les produits -> leurs IDs quand besoin
    ProducerCreateDTO toDto(Producer producer);


    /**
     * To entity user.
     *
     * @param dto the dto
     * @return the user
     */
// Convertir le RegisterDTO en une entité User
    @Mapping(target = "accountStatus", expression = "java(dto.accountStatus() != null ? AccountStatus.valueOf(dto.accountStatus()) : AccountStatus.ACTIVE)")
    User toEntity(RegisterDTO dto);

    /**
     * Update producer from dto.
     *
     * @param dto      the dto
     * @param producer the producer
     */
// Méthode pour mettre à jour une entité `Producer` partiellement via un DTO
    void updateProducerFromDto(ProducerUpdateDTO dto, @MappingTarget Producer producer);

    /**
     * Update entity from dto.
     *
     * @param dto    the dto
     * @param entity the entity
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "products", expression = "java(dto.getProductIds() == null ? new java.util.ArrayList<>() : null)")
    void updateEntityFromDto(ProducerCreateDTO dto, @MappingTarget Producer entity);

    /**
     * Update user from dto.
     *
     * @param dto    the dto
     * @param entity the entity
     */
// Met à jour un User existant avec les champs d'un RegisterDTO
    void updateUserFromDto(RegisterDTO dto, @MappingTarget User entity);

    /**
     * Map products to ids list.
     *
     * @param products the products
     * @return the list
     */
    default List<Long> mapProductsToIds(List<Product> products) {
        return (products == null || products.isEmpty())
                ? List.of()  // Retourne une liste vide au lieu de `null`
                : products.stream().map(Product::getId).toList();
    }

}


