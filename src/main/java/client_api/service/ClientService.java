package client_api.service;

import client_api.dto.ClientsPageRequest;
import client_api.dto.CreateClientRequest;
import client_api.dto.UpdateClientRequest;
import client_api.entity.Client;
import client_api.entity.Contact;
import client_api.exception.ResourceNotFoundException;
import client_api.mapper.ClientMapper;
import client_api.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientsPageRequest getAllClients(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientMapper.convertToList(clientsPage);
    }

    public Client createClient(CreateClientRequest dto) {
        Client client = clientMapper.convertToEntity(dto);
        // Устанавливаем связь между клиентом и контактами
        client.getContacts().forEach(contact -> contact.setClient(client));
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, UpdateClientRequest dto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        existingClient.setName(dto.getName());
        existingClient.setLastName(dto.getLastName());

        // Обрабатываем контакты напрямую, если они есть в dto
        if (dto.getContacts() != null && !dto.getContacts().isEmpty()) {
            // Очищаем старые контакты
            existingClient.getContacts().clear();

            // Привязываем новые контакты к клиенту
            dto.getContacts().forEach(contactDto -> {
                Contact contact = new Contact();
                contact.setPhone(contactDto.getPhone());
                contact.setEmail(contactDto.getEmail());
                contact.setClient(existingClient); // Связываем контакт с клиентом
                existingClient.getContacts().add(contact);
            });
        }
        return clientRepository.save(existingClient);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

