package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.OrderDetails;
import com.bkit12.app.service.dto.OrderDetailsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderDetails} and its DTO {@link OrderDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { DrinksMapper.class, BooksMapper.class, FoodsMapper.class, OrdersMapper.class })
public interface OrderDetailsMapper extends EntityMapper<OrderDetailsDTO, OrderDetails> {
    @Mapping(target = "drinks", source = "drinks", qualifiedByName = "idSet")
    @Mapping(target = "books", source = "books", qualifiedByName = "idSet")
    @Mapping(target = "foods", source = "foods", qualifiedByName = "idSet")
    @Mapping(target = "orders", source = "orders", qualifiedByName = "id")
    OrderDetailsDTO toDto(OrderDetails s);

    @Mapping(target = "removeDrinks", ignore = true)
    @Mapping(target = "removeBooks", ignore = true)
    @Mapping(target = "removeFoods", ignore = true)
    OrderDetails toEntity(OrderDetailsDTO orderDetailsDTO);
}
