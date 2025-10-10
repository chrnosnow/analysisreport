package com.example.analysisreport.contract.controller;

import com.example.analysisreport.contract.dto.ContractCreateDto;
import com.example.analysisreport.contract.dto.ContractResponseDto;
import com.example.analysisreport.contract.dto.ContractUpdateDto;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.service.ContractService;
import com.example.analysisreport.core.controller.BaseCrudController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/contracts")
public class ContractController extends BaseCrudController<Contract, Long, ContractCreateDto, ContractUpdateDto, ContractResponseDto> {
    public ContractController(ContractService contractService) {
        super(contractService);
    }
}
