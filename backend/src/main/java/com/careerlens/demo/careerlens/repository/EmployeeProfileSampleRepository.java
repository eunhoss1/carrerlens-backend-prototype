package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.EmployeeProfileSample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProfileSampleRepository extends JpaRepository<EmployeeProfileSample, Long> {
}
