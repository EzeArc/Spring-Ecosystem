package spring.ecosystem.rest_api_template.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import spring.ecosystem.rest_api_template.dto.CreateUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapea User → UserDTO
    UserDTO userToUserDTO(User user);

    // Mapea UserDTO → User
    @Mapping(target = "id", ignore = true) // Evita sobrescribir el ID al actualizar
    @Mapping(target = "createdBy", ignore = true) // Ignora los datos de auditoría
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "authorities", ignore = true) // Ignora authorities
    User userDTOToUser(UserDTO userDTO);

    // Mapea CreateUserDTO → User (para crear nuevos usuarios)
    @Mapping(target = "id", ignore = true) // El ID se genera automáticamente
    @Mapping(target = "createdBy", ignore = true) // Ignora los campos de auditoría
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "authorities", ignore = true) // Ignora authorities
    User createUserDTOToUser(CreateUserDTO createUserDTO);

    // Mapea User → CreateUserDTO
    CreateUserDTO userToCreateUserDTO(User user);
}