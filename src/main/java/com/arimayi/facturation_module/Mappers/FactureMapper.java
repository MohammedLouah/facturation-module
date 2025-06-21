package com.arimayi.facturation_module.Mappers;

import com.arimayi.facturation_module.DTOs.FactureDTO;
import com.arimayi.facturation_module.DTOs.FactureExportDTO;
import com.arimayi.facturation_module.Entities.Facture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LigneFactureMapper.class})
public interface FactureMapper {
    @Mapping(target = "clientId", source = "client.id") // Mapping personnalisé
    FactureDTO toDto(Facture facture);

    @Mapping(target = "client", ignore = true) // Géré manuellement dans le service
    Facture toEntity(FactureDTO factureDTO);

    @Mapping(target = "client", source = "client")
    @Mapping(target = "lignes", source = "lignes")
    FactureExportDTO toExportDto(Facture facture);
}
