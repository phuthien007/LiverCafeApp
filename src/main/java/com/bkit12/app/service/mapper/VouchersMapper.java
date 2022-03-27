package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Vouchers;
import com.bkit12.app.service.dto.VouchersDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vouchers} and its DTO {@link VouchersDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomersMapper.class })
public interface VouchersMapper extends EntityMapper<VouchersDTO, Vouchers> {
    @Mapping(target = "customers", source = "customers", qualifiedByName = "idSet")
    VouchersDTO toDto(Vouchers s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VouchersDTO toDtoId(Vouchers vouchers);

    @Mapping(target = "removeCustomers", ignore = true)
    Vouchers toEntity(VouchersDTO vouchersDTO);
}
