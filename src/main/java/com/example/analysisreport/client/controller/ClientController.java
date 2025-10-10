package com.example.analysisreport.client.controller;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

//    @GetMapping
//    public List<Client> getAllClients() {
//        return this.clientRepository.findAll();
//    }

    @GetMapping("/ascending")
    public List<Client> getAllClientsInAscendingOrder() {
        return this.clientRepository.findByOrderByNameAsc();
    }

    @GetMapping("/{name}")
    public List<Client> getByNameContaining(@PathVariable(value = "name") String clientName) {
        return this.clientRepository.findAllByNameContainingIgnoreCase(clientName);
    }

    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such client.");
        }

        Client client = optionalClient.get();
        return client;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Client addClient(@RequestBody Client client) {
        String name = client.getName();
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return this.clientRepository.save(client);
    }

    @PutMapping("update/{clientId}")
    //method uses null as input for the client details that don't require any change
    public Client updateClientDetails(@PathVariable("clientId") Long id, @RequestBody Client updatedDetails) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Client client = optionalClient.get();
        String updatedName = updatedDetails.getName();
        String updatedPAddress = updatedDetails.getAddress();
        if (updatedName != null) {
            client.setName(updatedName);
        }

        if (updatedPAddress != null) {
            client.setAddress(updatedPAddress);
        }
        return this.clientRepository.save(client);
    }

    @DeleteMapping("client/{id}")
    public Client deleteClient(@PathVariable(value = "id") Long id) {
        Optional<Client> optionalClientToDelete = clientRepository.findById(id);
        if (optionalClientToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        Client clientToDelete = optionalClientToDelete.get();
        clientRepository.deleteById(id);
        return clientToDelete;
    }

}
