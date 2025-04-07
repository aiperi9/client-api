package client_api;

import client_api.entity.Client;
import client_api.entity.Contact;
import client_api.repository.ClientRepository;
import client_api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void run(String... args) throws Exception {
        // Проверим, если данные уже есть, не будем добавлять новые.
        if (clientRepository.count() == 0) {
            Client client1 = new Client();
            client1.setName("Иван");
            client1.setLastName("Иванов");
            client1 = clientRepository.save(client1);

            Client client2 = new Client();
            client2.setName("Айгуль");
            client2.setLastName("Абдуллаева");
            client2 = clientRepository.save(client2);

            Contact contact1 = new Contact();
            contact1.setEmail("aigul@example.com");
            contact1.setPhone("+996700000002");
            contact1.setClient(client2);
            contactRepository.save(contact1);

            Contact contact2 = new Contact();
            contact2.setEmail("ivan@example.com");
            contact2.setPhone("+996700000001");
            contact2.setClient(client1);
            contactRepository.save(contact2);
        }
    }
}
