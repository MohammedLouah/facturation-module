package com.arimayi.facturation_module.Services;

import com.arimayi.facturation_module.DTOs.FactureDTO;
import com.arimayi.facturation_module.DTOs.FactureExportDTO;
import com.arimayi.facturation_module.DTOs.LigneFactureDTO;
import com.arimayi.facturation_module.Entities.Client;
import com.arimayi.facturation_module.Entities.Facture;
import com.arimayi.facturation_module.Entities.LigneFacture;
import com.arimayi.facturation_module.Exceptions.ClientNotFoundException;
import com.arimayi.facturation_module.Exceptions.FactureValidationException;
import com.arimayi.facturation_module.Mappers.FactureMapper;
import com.arimayi.facturation_module.Repositories.ClientRepository;
import com.arimayi.facturation_module.Repositories.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {
    private final FactureRepository factureRepository;
    private final ClientRepository clientRepository;
    private final FactureMapper factureMapper;

    // Récupère toutes les factures existantes en base, les convertit en DTO et les retourne.
    public List<FactureDTO> getAllFactures() {
        return factureRepository.findAll().stream()
                .map(factureMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Crée une nouvelle facture après validation et calcul des totaux.
     * Associe également la facture à un client existant.
     */
    public FactureDTO createFacture(FactureDTO factureDTO) {

        // Valide les règles métier sur la facture
        validateFacture(factureDTO);

        // Si la date n'est pas fournie, utiliser la date actuelle
        if (factureDTO.getDate() == null) {
            factureDTO.setDate(LocalDate.now());
        }

        // Récupère le client associé à la facture
        Client client = clientRepository.findById(factureDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(factureDTO.getClientId()));

        // Convertit le DTO en entité
        Facture facture = factureMapper.toEntity(factureDTO);
        facture.setClient(client);

        // Associer chaque ligne à la facture
        if (facture.getLignes() != null) {
            for (LigneFacture ligne : facture.getLignes()) {
                ligne.setFacture(facture);
            }
        }

        calculerTotaux(facture);

        // Enregistre et retourne le DTO de la facture créée
        return factureMapper.toDto(factureRepository.save(facture));
    }

    /**
     * Exporte une facture au format structuré JSON.
     * Si la facture n'existe pas, une exception est levée.
     */
    public FactureExportDTO exportFacture(Integer id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new FactureValidationException("Facture non trouvée"));
        return factureMapper.toExportDto(facture);
    }

    private void calculerTotaux(Facture facture) {
        double totalHT = 0;
        double totalTVA = 0;

        for (LigneFacture ligne : facture.getLignes()) {
            double montantHT = ligne.getQuantite() * ligne.getPrixUnitaireHT();
            double montantTVA = montantHT * (ligne.getTauxTVA().valeur / 100);

            totalHT += montantHT;
            totalTVA += montantTVA;
        }

        facture.setTotalHT(totalHT);
        facture.setTotalTVA(totalTVA);
        facture.setTotalTTC(totalHT + totalTVA);
    }

    /**
     * Permet de rechercher des factures selon 3 cas :
     * - Par client et date
     * - Par client seulement
     * - Par date seulement
     * - Sinon retourne toutes les factures
     */
    public List<FactureDTO> searchFactures(Integer clientId, String dateStr) {
        Optional<Integer> clientOpt = Optional.ofNullable(clientId);
        Optional<LocalDate> dateOpt = parseOptionalDate(dateStr);

        List<Facture> factures;

        if (clientOpt.isPresent() && dateOpt.isPresent()) {
            factures = factureRepository.findByClientIdAndDate(clientId, dateOpt.get());
        } else if (clientOpt.isPresent()) {
            factures = factureRepository.findByClientId(clientId);
        } else if (dateOpt.isPresent()) {
            factures = factureRepository.findByDate(dateOpt.get());
        } else {
            return getAllFactures();
        }

        return factures.stream()
                .map(factureMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Essaie de parser une date au format String en LocalDate.
     * Retourne Optional.empty() si la chaîne est invalide ou vide.
     */
    private Optional<LocalDate> parseOptionalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(dateStr.trim()));
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide: "+dateStr);
            return Optional.empty();
        }
    }

    /**
     * Valide les règles métier d'une facture :
     * - Au moins une ligne
     * - Aucun champ vide
     * - Taux de TVA autorisés seulement (0, 5.5, 10, 20)
     */
    private void validateFacture(FactureDTO factureDTO) {
        if (factureDTO.getLignes() == null || factureDTO.getLignes().isEmpty()) {
            throw new FactureValidationException("Une facture doit avoir au moins une ligne");
        }

        for (LigneFactureDTO ligne : factureDTO.getLignes()) {
            if (ligne.getDescription() == null || ligne.getDescription().isEmpty() ||
                    ligne.getQuantite() <= 0 || ligne.getPrixUnitaireHT() <= 0) {
                throw new FactureValidationException("Tous les champs des lignes de facture doivent être remplis");
            }

            if (ligne.getTauxTVA().valeur != 0 && ligne.getTauxTVA().valeur != 5.5 &&
                    ligne.getTauxTVA().valeur != 10 && ligne.getTauxTVA().valeur != 20) {
                throw new FactureValidationException("Taux de TVA invalide. Les valeurs autorisées sont : 0%, 5.5%, 10%, 20%");
            }
        }
    }

}

