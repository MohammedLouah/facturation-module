package com.arimayi.facturation_module.Mappers;

import com.arimayi.facturation_module.DTOs.ClientDTO;
import com.arimayi.facturation_module.Entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}
