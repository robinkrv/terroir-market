package fr.ecommerce.mappers;

import fr.ecommerce.dto.UserDTO;
import fr.ecommerce.dto.UserUpdateDTO;
import fr.ecommerce.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * The interface User mapper.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * The constant INSTANCE.
     */
// Instance du mapper pour pouvoir l'utiliser directement si nécessaire
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Update user from dto.
     *
     * @param dto  the dto
     * @param user the user
     */
// Méthode pour mapper les données d'un DTO vers une entité User
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);

    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDTO toDTO(User user);
}

