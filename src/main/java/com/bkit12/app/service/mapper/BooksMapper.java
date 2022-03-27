package com.bkit12.app.service.mapper;

import com.bkit12.app.domain.Books;
import com.bkit12.app.service.dto.BooksDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Books} and its DTO {@link BooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BooksMapper extends EntityMapper<BooksDTO, Books> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<BooksDTO> toDtoIdSet(Set<Books> books);
}
