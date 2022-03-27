package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Users;
import com.bkit12.app.service.dto.UsersDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Users} and its DTO {@link UsersDTO}.
 */
@Mapper(componentModel = "spring", uses = { RolesMapper.class })
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "idSet")
    UsersDTO toDto(Users s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsersDTO toDtoId(Users users);

    @Mapping(target = "removeRoles", ignore = true)
    Users toEntity(UsersDTO usersDTO);
}
