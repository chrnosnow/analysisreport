package com.example.analysisreport.admin.repository;

import com.example.analysisreport.admin.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    List<Admin> findAll();
}
