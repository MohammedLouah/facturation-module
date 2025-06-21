package com.arimayi.facturation_module.Controllers;

import com.arimayi.facturation_module.DTOs.FactureDTO;
import com.arimayi.facturation_module.DTOs.FactureExportDTO;
import com.arimayi.facturation_module.Entities.Facture;
import com.arimayi.facturation_module.Services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/facture")
public class FactureController {
    private final FactureService factureService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FactureDTO> getAllFactures() {
        return factureService.getAllFactures();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FactureDTO createFacture(@RequestBody FactureDTO factureDTO) {
        return factureService.createFacture(factureDTO);
    }

    @GetMapping(value = "/{id}/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public FactureExportDTO exportFacture(@PathVariable Integer id) {
        return factureService.exportFacture(id);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FactureDTO> searchFactures(
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) String date) {
        return factureService.searchFactures(clientId, date);
    }

}
