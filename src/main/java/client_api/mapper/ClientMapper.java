package client_api.mapper;

import client_api.dto.ClientsPageRequest;
import client_api.dto.CreateClientRequest;
import client_api.dto.UpdateClientRequest;
import client_api.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { ContactMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    @Mapping(target = "contacts", source = "contacts")
    Client convertToEntity(CreateClientRequest dto);

    @Mapping(target = "contacts", source = "contacts")
    Client convertToEntity(UpdateClientRequest dto);

    @Mapping(target = "contacts", source = "contacts")
    CreateClientRequest convertToDto(Client client);

    @Mapping(source = "content", target = "list")
    ClientsPageRequest convertToList(Page<Client> userPage);
}
