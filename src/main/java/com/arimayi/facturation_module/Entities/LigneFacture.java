package com.arimayi.facturation_module.Entities;

import com.arimayi.facturation_module.Entities.Enums.Tva;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "lignesFacture")
public class LigneFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Facture facture;

    @NotBlank
    private String description;

    private int quantite;
    private double prixUnitaireHT;

    @Enumerated(EnumType.STRING)
    private Tva tauxTVA;
}