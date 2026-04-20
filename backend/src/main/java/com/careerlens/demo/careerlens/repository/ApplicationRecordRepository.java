package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.ApplicationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {
    List<ApplicationRecord> findByUserId(Long userId);
}
