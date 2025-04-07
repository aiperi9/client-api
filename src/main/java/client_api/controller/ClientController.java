package client_api.controller;

import client_api.dto.ClientsPageRequest;
import client_api.dto.CreateClientRequest;
import client_api.dto.UpdateClientRequest;
import client_api.entity.Client;
import client_api.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<ClientsPageRequest> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ClientsPageRequest clientsPage = clientService.getAllClients(PageRequest.of(page, size));
        return ResponseEntity.ok(clientsPage);
    }

    @GetMapping("/{clientId}")
    public Client getClient(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping
    public Client createClient(@Valid @RequestBody CreateClientRequest dto) {
        return clientService.createClient(dto);
    }

    @PutMapping("/{clientId}")
    public Client updateClient(@PathVariable Long clientId, @Valid @RequestBody UpdateClientRequest dto) {
        return clientService.updateClient(clientId, dto);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }
}

