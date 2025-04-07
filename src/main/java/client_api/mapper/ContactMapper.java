package client_api.mapper;

import client_api.dto.ContactDto;
import client_api.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {

    Contact convertToEntity(ContactDto dto);

    ContactDto convertToDto(Contact entity);
}
