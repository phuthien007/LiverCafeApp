package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Orders;
import com.bkit12.app.service.dto.OrdersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = { PaymentsMapper.class, CustomersMapper.class })
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {
    @Mapping(target = "payment", source = "payment", qualifiedByName = "id")
    @Mapping(target = "customers", source = "customers", qualifiedByName = "id")
    OrdersDTO toDto(Orders s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdersDTO toDtoId(Orders orders);
}
