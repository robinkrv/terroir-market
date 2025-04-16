package fr.ecommerce.mappers;

import fr.ecommerce.dto.ProducerCreateDTO;
import fr.ecommerce.dto.ProducerUpdateDTO;
import fr.ecommerce.dto.RegisterDTO;
import fr.ecommerce.models.entities.Producer;
import fr.ecommerce.models.entities.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProducerMapper {

    // Instance pour les tests indépendants de Spring si besoin
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "products", ignore = true)
    Producer toEntity(ProducerCreateDTO dto);

    // Convertir le RegisterDTO en une entité User
    @Mapping(target = "accountStatus", expression = "java(dto.accountStatus() != null ? AccountStatus.valueOf(dto.accountStatus()) : AccountStatus.ACTIVE)")
    User toEntity(RegisterDTO dto);

    // Méthode pour mettre à jour une entité `Producer` partiellement via un DTO
    void updateProducerFromDto(ProducerUpdateDTO dto, @MappingTarget Producer producer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProducerCreateDTO dto, @MappingTarget Producer entity);

    // Met à jour un User existant avec les champs d'un RegisterDTO
    void updateUserFromDto(RegisterDTO dto, @MappingTarget User entity);
}


