package com.example.analysisreport.admin.controller;

import com.example.analysisreport.admin.entity.Admin;
import com.example.analysisreport.admin.repository.AdminRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/admins")
public class AdminController {
    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @PostMapping("/add")
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

    @PutMapping("/update/{adminId}")
    public Admin updateAdmin(@PathVariable(value = "adminId") Long adminId, @RequestBody String json) throws JsonProcessingException {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Admin adminToUpdate = adminOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(adminToUpdate);
        adminToUpdate = reader.readValue(json);
        return adminRepository.save(adminToUpdate);
    }

    @DeleteMapping("/delete/{adminId}")
    public Admin deleteAdmin(@PathVariable(value = "adminId") Long adminId, @RequestBody String json) throws JsonProcessingException {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Admin adminToDelete = adminOptional.get();
        adminRepository.deleteById(adminId);
        return adminToDelete;
    }

}
