package com.arimayi.facturation_module.DTOs;

import com.arimayi.facturation_module.Entities.Enums.Tva;
import lombok.Data;

@Data
public class LigneFactureDTO {
    private String description;
    private int quantite;
    private double prixUnitaireHT;
    private Tva tauxTVA;
}
