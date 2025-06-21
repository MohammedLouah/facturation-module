package com.arimayi.facturation_module.Services;

import com.arimayi.facturation_module.DTOs.ClientDTO;
import com.arimayi.facturation_module.Entities.Client;
import com.arimayi.facturation_module.Exceptions.ClientNotFoundException;
import com.arimayi.facturation_module.Mappers.ClientMapper;
import com.arimayi.facturation_module.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Récupère tous les clients de la base de données et les convertit en DTO.
     */
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Crée un nouveau client à partir d’un ClientDTO.
     * Si la date de création n’est pas fournie, elle est définie à aujourd’hui.
     */
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientDTO.getDateCreation() == null) {
            clientDTO.setDateCreation(LocalDate.now());
        }
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    /**
     * Récupère un client par son ID.
     * Si le client n'existe pas, une exception personnalisée est levée.
     */
    public ClientDTO getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        return clientMapper.toDto(client);
    }
}
