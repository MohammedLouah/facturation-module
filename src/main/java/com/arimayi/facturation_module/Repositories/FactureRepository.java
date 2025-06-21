package com.arimayi.facturation_module.Repositories;

import com.arimayi.facturation_module.Entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Integer> {
    List<Facture> findByClientId(Integer clientId);
    List<Facture> findByDate(LocalDate date);
    List<Facture> findByClientIdAndDate(Integer clientId, LocalDate date);
}
