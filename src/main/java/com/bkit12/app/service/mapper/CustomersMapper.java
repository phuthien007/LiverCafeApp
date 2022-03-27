package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Customers;
import com.bkit12.app.service.dto.CustomersDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customers} and its DTO {@link CustomersDTO}.
 */
@Mapper(componentModel = "spring", uses = { UsersMapper.class })
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    CustomersDTO toDto(Customers s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomersDTO toDtoId(Customers customers);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<CustomersDTO> toDtoIdSet(Set<Customers> customers);
}
