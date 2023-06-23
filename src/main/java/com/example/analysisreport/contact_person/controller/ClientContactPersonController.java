package com.example.analysisreport.contact_person.controller;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contact_person.entity.ClientContactPerson;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contact_person.repository.ClientContactPersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contact-persons")
public class ClientContactPersonController {

    private ClientRepository clientRepository;
    private ClientContactPersonRepository clientContactPersonRepository;

    public ClientContactPersonController(ClientRepository clientRepository,
                                         ClientContactPersonRepository clientContactPersonRepository) {
        this.clientRepository = clientRepository;
        this.clientContactPersonRepository = clientContactPersonRepository;
    }

    @GetMapping
    public List<ClientContactPerson> getAll() {
        return this.clientContactPersonRepository.findAll();
    }

    @GetMapping("/{client_name}")
    //method to get contact person by client name (partial name inclusive) using native query
    //an alternative using JpaSpecification presented below
    public List<ClientContactPerson> getByClientNameLike(@PathVariable(value = "client_name") String clientName) {

        if (clientName.isEmpty()) {
            return null;
        }

        String lowerCaseName = clientName.toLowerCase();
        return this.clientContactPersonRepository.findAllByClientNameContaining(lowerCaseName);
    }

    @PostMapping("/add/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientContactPerson addContactPerson(@PathVariable(value = "clientId") Long clientId,
                                                @RequestBody ClientContactPerson person) {

        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Client client = optionalClient.get();
        person.setClient(client);

        return clientContactPersonRepository.save(person);
    }

    @PutMapping("update/{contactPersonId}")
    //can partially update the contact person's details
    //will update only the fields with non-null values
    //will give error on null values
    public ClientContactPerson updateContactPersonDetails(@PathVariable(value = "contactPersonId") Long id,
                                                          @RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Optional<ClientContactPerson> optionalPerson = clientContactPersonRepository.findById(id);
        if (optionalPerson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        ClientContactPerson person = optionalPerson.get();
        ObjectReader reader = mapper.readerForUpdating(person);
        person = reader.readValue(json);
        clientContactPersonRepository.save(person);
        return person;
    }

    @DeleteMapping("delete/{contactPersonId}")
    public ClientContactPerson deleteContactPerson(@PathVariable(value = "contactPersonId") Long id) {
        Optional<ClientContactPerson> optionalPerson = clientContactPersonRepository.findById(id);
        if (optionalPerson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        ClientContactPerson personToDelete = optionalPerson.get();
        clientContactPersonRepository.deleteById(id);
        return personToDelete;
    }


    //    @GetMapping("/{client_name}")
    //    //method to get contact person by client name (partial name included) using JpaSpecifications
    //    public List<ClientContactPerson> getByClientName(@PathVariable(value="client_name") String clientName){
    //        if(clientName.isEmpty()){
    //            return null;
    //        }
    //        Specification<ClientContactPerson> spec = hasClientWithName(clientName);
    //        List<ClientContactPerson> persons = this.clientContactPersonRepository.findAll(spec);
    //        return persons;
    //    }

}
