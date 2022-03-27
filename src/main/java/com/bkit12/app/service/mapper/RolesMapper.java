package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Roles;
import com.bkit12.app.service.dto.RolesDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Roles} and its DTO {@link RolesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RolesMapper extends EntityMapper<RolesDTO, Roles> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RolesDTO> toDtoIdSet(Set<Roles> roles);
}
