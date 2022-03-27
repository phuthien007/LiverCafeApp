package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.WorkingSpaceForms;
import com.bkit12.app.service.dto.WorkingSpaceFormsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingSpaceForms} and its DTO {@link WorkingSpaceFormsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdersMapper.class, PaymentsMapper.class, ServicesMapper.class, CustomersMapper.class })
public interface WorkingSpaceFormsMapper extends EntityMapper<WorkingSpaceFormsDTO, WorkingSpaceForms> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    @Mapping(target = "payment", source = "payment", qualifiedByName = "id")
    @Mapping(target = "services", source = "services", qualifiedByName = "idSet")
    @Mapping(target = "customers", source = "customers", qualifiedByName = "id")
    WorkingSpaceFormsDTO toDto(WorkingSpaceForms s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<WorkingSpaceFormsDTO> toDtoIdSet(Set<WorkingSpaceForms> workingSpaceForms);

    @Mapping(target = "removeServices", ignore = true)
    WorkingSpaceForms toEntity(WorkingSpaceFormsDTO workingSpaceFormsDTO);
}
