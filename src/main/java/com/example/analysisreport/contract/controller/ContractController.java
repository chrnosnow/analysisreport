package com.example.analysisreport.contract.controller;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contracts")
public class ContractController {
    private ContractRepository contractRepository;
    private ClientRepository clientRepository;

    public ContractController(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @GetMapping("/{client_name}")
    public List<Contract> getAllByClientName(@PathVariable(value = "client_name") String clientName) {
        if (clientName.isEmpty()) {
            return null;
        }

        return contractRepository.findAllByClientNameContainingIgnoreCase(clientName);
    }

    @PostMapping("/add/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Contract addContract(@PathVariable(value = "clientId") Long clientId, @RequestBody Contract contract) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Client client = optionalClient.get();
        contract.setClient(client);

        return contractRepository.save(contract);
    }

    @PutMapping("/update/{contractId}")
    //can partially update the contract details
    //will update only the fields with non-null values
    //will give error on null values
    public Contract updateContract(@PathVariable(value = "contractId") Long id, @RequestBody String json) throws JsonProcessingException {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Contract contractToUpdate = optionalContract.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(contractToUpdate);
        contractToUpdate = reader.readValue(json);
        contractRepository.save(contractToUpdate);
        return contractToUpdate;
    }

    @DeleteMapping("/delete/{contractId}")
    public Contract deleteContract(@PathVariable(value = "contractId") Long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Contract contractToDelete = optionalContract.get();
        contractRepository.deleteById(id);
        return contractToDelete;
    }
}
