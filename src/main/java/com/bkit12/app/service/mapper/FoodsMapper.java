package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Foods;
import com.bkit12.app.service.dto.FoodsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Foods} and its DTO {@link FoodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoodsMapper extends EntityMapper<FoodsDTO, Foods> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<FoodsDTO> toDtoIdSet(Set<Foods> foods);
}
