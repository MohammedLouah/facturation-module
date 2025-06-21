package com.arimayi.facturation_module.Mappers;

import com.arimayi.facturation_module.DTOs.LigneFactureDTO;
import com.arimayi.facturation_module.Entities.LigneFacture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LigneFactureMapper {
    LigneFactureDTO toDto(LigneFacture ligne);
    LigneFacture toEntity(LigneFactureDTO ligneDTO);
}
