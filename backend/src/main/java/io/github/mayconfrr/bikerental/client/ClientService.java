package io.github.mayconfrr.bikerental.client;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(ClientDto clientDto) {
        Client client = Client.builder()
                .cpf(clientDto.cpf())
                .name(clientDto.name())
                .email(clientDto.email())
                .phone(clientDto.phone())
                .build();

        // TODO: handle duplicated cpf
        // TODO: handle duplicated email

        return clientRepository.save(client);
    }

    @Transactional
    public void updateClientById(Long id, ClientDto clientDto) {
        Client updatedClient = clientRepository.findById(id)
                .map(client -> client.withCpf(clientDto.cpf()))
                .map(client -> client.withName(clientDto.name()))
                .map(client -> client.withEmail(clientDto.email()))
                .map(client -> client.withPhone(clientDto.phone()))
                .orElseThrow(() -> new ClientNotFoundException(id));

        // TODO: handle duplicated cpf
        // TODO: handle duplicated email

        clientRepository.save(updatedClient);
    }

    @Transactional
    public void deleteClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
    }
}
