package com.example.analysisreport.client.controller;

import com.example.analysisreport.client.dto.ClientCreateDto;
import com.example.analysisreport.client.dto.ClientResponseDto;
import com.example.analysisreport.client.dto.ClientUpdateDto;
import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.service.ClientService;
import com.example.analysisreport.core.controller.BaseCrudController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2/clients")
public class ClientController extends BaseCrudController<Client, Long, ClientCreateDto, ClientUpdateDto,
        ClientResponseDto> {

    public ClientController(ClientService clientService) {
        super(clientService);
    }
}
