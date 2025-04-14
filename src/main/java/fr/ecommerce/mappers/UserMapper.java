package fr.ecommerce.mappers;

import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.dto.UserUpdateDTO;
import fr.ecommerce.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // Instance du mapper pour pouvoir l'utiliser directement si nécessaire
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Méthode pour mapper les données d'un DTO vers une entité User
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);

    UserDTO toDTO(User user);
}

