package io.github.mayconfrr.bikerental.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        log.info("GET request for all clients");
        return clientService.getClients();
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
        log.info("GET request for client with id: {}", id);
        return ResponseEntity.of(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDto clientDto) {
        log.info("POST request for client with DTO: {}", clientDto);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .build(clientService.saveClient(clientDto).getId())
        ).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateClientByIdd(@PathVariable("id") Long id, @Valid @RequestBody ClientDto clientDto) {
        log.info("PUT request for client with id: {} and DTO: {}", id, clientDto);
        clientService.updateClientById(id, clientDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable("id") Long id) {
        log.info("DELETE request for client with id: {}", id);
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Void> handleClientNotFound(ClientNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
