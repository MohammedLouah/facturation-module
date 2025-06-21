package com.arimayi.facturation_module.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FactureExportDTO {
    private Integer id;
    private LocalDate date;
    private ClientDTO client;
    private List<LigneFactureDTO> lignes;
    private double totalHT;
    private double totalTVA;
    private double totalTTC;
}