package spring.ecosystem.rest_api_template.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import spring.ecosystem.rest_api_template.dto.CreateUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;

@Mapper
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    User toUser(CreateUserDTO createUserDTO);
}
