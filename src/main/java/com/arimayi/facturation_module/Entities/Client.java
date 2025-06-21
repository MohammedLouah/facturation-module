package com.arimayi.facturation_module.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nom;

    @Email
    private String email;

    @NotBlank
    private String siret;

    private LocalDate dateCreation;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Facture> factures = new ArrayList<>();
}
