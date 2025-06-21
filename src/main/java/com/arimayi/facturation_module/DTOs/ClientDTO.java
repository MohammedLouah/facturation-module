package com.arimayi.facturation_module.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {
    private Integer id;
    private String nom;
    private String email;
    private String siret;
    private LocalDate dateCreation;
}
