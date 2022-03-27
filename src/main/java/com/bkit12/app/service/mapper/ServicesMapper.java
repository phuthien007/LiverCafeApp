package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Services;
import com.bkit12.app.service.dto.ServicesDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Services} and its DTO {@link ServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServicesMapper extends EntityMapper<ServicesDTO, Services> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ServicesDTO> toDtoIdSet(Set<Services> services);
}
