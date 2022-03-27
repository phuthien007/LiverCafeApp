package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Drinks;
import com.bkit12.app.service.dto.DrinksDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drinks} and its DTO {@link DrinksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrinksMapper extends EntityMapper<DrinksDTO, Drinks> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<DrinksDTO> toDtoIdSet(Set<Drinks> drinks);
}
