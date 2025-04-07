package client_api.controller;

import client_api.dto.ContactDto;
import client_api.dto.CreateClientRequest;
import client_api.entity.Client;
import client_api.entity.Contact;
import client_api.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void createClient_ShouldReturnCreated() throws Exception {
        // Arrange
        CreateClientRequest request = new CreateClientRequest();
        request.setName("John");
        request.setLastName("Doe");
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone("986868686");
        contactDto.setEmail("email@email.com");
        request.setContacts(List.of(contactDto));

        Client client = new Client();
        client.setClientId(1L);
        client.setName("John");
        client.setLastName("Doe");
        Contact contact = new Contact();
        contact.setPhone("986868686");
        contact.setEmail("email@email.com");
        client.setContacts(List.of(contact));

        when(clientService.createClient(request)).thenReturn(client);

        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(1));
    }
}