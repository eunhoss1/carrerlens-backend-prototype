package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.SettlementChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementChecklistRepository extends JpaRepository<SettlementChecklist, Long> {
    List<SettlementChecklist> findByCountryIgnoreCase(String country);
}
