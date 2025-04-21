package fr.ecommerce.mappers;

import fr.ecommerce.dto.ProductDTO;
import fr.ecommerce.models.entities.Product;
import fr.ecommerce.models.entities.Producer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "producer", source = "producerId", qualifiedByName = "mapProducerIdToProducer")
    Product toEntity(ProductDTO dto);

    // Mapping pour convertir une entité Product en DTO
    @Mapping(target = "producerId", source = "producer.id")
    ProductDTO toDto(Product product);

    // Méthode pour gérer les updates partiels
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductDTO dto, @MappingTarget Product product);

    // Méthode auxiliaire pour mapper un ID à un objet Producer
    @Named("mapProducerIdToProducer")
    default Producer mapProducerIdToProducer(Long producerId) {
        if (producerId == null) {
            throw new IllegalArgumentException("Producer ID ne peut pas être null");
        }
        Producer producer = new Producer();
        producer.setId(producerId); // Associe uniquement l'ID
        return producer;
    }
}
