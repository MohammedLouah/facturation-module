package com.arimayi.facturation_module.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FactureDTO {
    private Integer id;
    private Integer clientId;
    private LocalDate date;
    private List<LigneFactureDTO> lignes;
}
