package client_api.service;

import client_api.dto.ContactDto;
import client_api.dto.CreateClientRequest;
import client_api.dto.UpdateClientRequest;
import client_api.entity.Client;
import client_api.entity.Contact;
import client_api.exception.ResourceNotFoundException;
import client_api.mapper.ClientMapper;
import client_api.repository.ClientRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @SneakyThrows
    @Test
    public void testCreateClient() {
        // Arrange
        CreateClientRequest request = new CreateClientRequest();
        request.setName("John");
        request.setLastName("Doe");
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone("986868686");
        contactDto.setEmail("email@email.com");
        request.setContacts(List.of(contactDto));

        // Создание сущности Client, как будет возвращено маппером
        Client client = new Client();
        client.setClientId(1L);
        client.setName("John");
        client.setLastName("Doe");

        Contact contact = new Contact();
        contact.setPhone("986868686");
        contact.setEmail("email@email.com");
        client.setContacts(List.of(contact));

        // Мокирование вызова маппера
        when(clientMapper.convertToEntity(any(CreateClientRequest.class))).thenReturn(client);
        // Мокирование сохранения клиента в репозитории
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client response = clientService.createClient(request);

        // Assert
        assertNotNull(response);
        assertEquals("John", response.getName());
        assertEquals("Doe", response.getLastName());
        assertEquals(1, response.getContacts().size());
        assertEquals("986868686", response.getContacts().get(0).getPhone());
        assertEquals("email@email.com", response.getContacts().get(0).getEmail());
    }

    @Test
    public void testUpdateClient_ClientNotFound() {
        // Arrange
        Long clientId = 1L;
        UpdateClientRequest request = new UpdateClientRequest();
        request.setName("John Updated");
        request.setLastName("Doe Updated");

        // Мокаем, что клиент не найден в репозитории
        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.updateClient(clientId, request);
        });
    }

    @SneakyThrows
    @Test
    public void testUpdateClient() {
        // Arrange
        Long clientId = 1L;
        UpdateClientRequest request = new UpdateClientRequest();
        request.setName("John Updated");
        request.setLastName("Doe Updated");

        // Существующий клиент
        Client existingClient = new Client();
        existingClient.setClientId(clientId);
        existingClient.setName("John");
        existingClient.setLastName("Doe");
        Contact contact = new Contact();
        contact.setPhone("986868686");
        contact.setEmail("email@email.com");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);  // Теперь коллекция изменяемая
        existingClient.setContacts(contactList);

        // Обновленный клиент
        Client updatedClient = new Client();
        updatedClient.setClientId(clientId);
        updatedClient.setName("John Updated");
        updatedClient.setLastName("Doe Updated");
        Contact contact1 = new Contact();
        contact1.setPhone("986868686");
        contact1.setEmail("email@email.com");
        List<Contact> contactList1 = new ArrayList<>();
        contactList1.add(contact1);  // Должно быть contactList1, а не contactList
        updatedClient.setContacts(contactList1);

        // Мокаем поведение репозитория
        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        // Act
        Client result = clientService.updateClient(clientId, request);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("Doe Updated", result.getLastName());
        assertEquals(1, result.getContacts().size());  // Убедись, что контакты обновились
        assertEquals("986868686", result.getContacts().get(0).getPhone());
        assertEquals("email@email.com", result.getContacts().get(0).getEmail());
    }

}
