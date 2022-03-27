package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.WorkingSpaces;
import com.bkit12.app.service.dto.WorkingSpacesDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingSpaces} and its DTO {@link WorkingSpacesDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorkingSpaceFormsMapper.class })
public interface WorkingSpacesMapper extends EntityMapper<WorkingSpacesDTO, WorkingSpaces> {
    @Mapping(target = "workingSpaceForms", source = "workingSpaceForms", qualifiedByName = "idSet")
    WorkingSpacesDTO toDto(WorkingSpaces s);

    @Mapping(target = "removeWorkingSpaceForms", ignore = true)
    WorkingSpaces toEntity(WorkingSpacesDTO workingSpacesDTO);
}
