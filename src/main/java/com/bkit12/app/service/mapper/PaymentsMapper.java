package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Payments;
import com.bkit12.app.service.dto.PaymentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payments} and its DTO {@link PaymentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { VouchersMapper.class })
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {
    @Mapping(target = "vouchers", source = "vouchers", qualifiedByName = "id")
    PaymentsDTO toDto(Payments s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentsDTO toDtoId(Payments payments);
}
