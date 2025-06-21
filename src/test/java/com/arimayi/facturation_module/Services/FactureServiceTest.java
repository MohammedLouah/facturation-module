package com.arimayi.facturation_module.Services;

import com.arimayi.facturation_module.DTOs.ClientDTO;
import com.arimayi.facturation_module.DTOs.FactureDTO;
import com.arimayi.facturation_module.DTOs.FactureExportDTO;
import com.arimayi.facturation_module.DTOs.LigneFactureDTO;
import com.arimayi.facturation_module.Entities.Client;
import com.arimayi.facturation_module.Entities.Enums.Tva;
import com.arimayi.facturation_module.Entities.Facture;
import com.arimayi.facturation_module.Exceptions.FactureValidationException;
import com.arimayi.facturation_module.Mappers.FactureMapper;
import com.arimayi.facturation_module.Repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FactureServiceTest {

    @Autowired
    private FactureService factureService;
    @Autowired
    private FactureMapper factureMapper;
    @Autowired
    private ClientService clientService;

    @Test
    public void testCreateFactureWithoutLinesShouldThrowException(){
        FactureDTO factureDTO = new FactureDTO();
        factureDTO.setClientId(100);
        factureDTO.setDate(LocalDate.now());
        factureDTO.setLignes(Collections.emptyList());

        assertThrows(FactureValidationException.class, () -> {
            factureService.createFacture(factureDTO);
        });
    }

    @Test
    public void testCalculateTotals() {
        ClientDTO client = new ClientDTO();
        client.setNom("test");
        client.setEmail("test@gmail.com");
        client.setSiret("jhssugdnwn");
        clientService.createClient(client);

        FactureDTO factureDTO = new FactureDTO();
        factureDTO.setClientId(1);
        factureDTO.setDate(LocalDate.now());

        LigneFactureDTO ligne1 = new LigneFactureDTO();
        ligne1.setDescription("Produit 1");
        ligne1.setQuantite(2);
        ligne1.setPrixUnitaireHT(100);
        ligne1.setTauxTVA(Tva.TVA_20); // reelement (comme dans Postman) on utilise la valeur numerique (double)

        factureDTO.setLignes(Collections.singletonList(ligne1));

        FactureDTO created = factureService.createFacture(factureDTO);
        FactureExportDTO createdExport = factureService.exportFacture(created.getId());

        assertEquals(200, createdExport.getTotalHT());
        assertEquals(40, createdExport.getTotalTVA());
        assertEquals(240, createdExport.getTotalTTC());
    }

}
