package com.example.backend.Controller;


import com.example.backend.Services.CommandeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commande")
public class CommandeController {
    @Autowired
    CommandeInterface commandeInterface;

    ///  ajout commande ///
    @PostMapping("/passer/{idUser}/{idTable}")
    public Map<String, Object> passerCommande(@PathVariable Long idUser, @PathVariable Long idTable , @RequestBody List<Long> platIds) {
        return commandeInterface.passerCommande(idUser, idTable, platIds);
    }

    // Endpoint pour supprimer une commande par son ID
    @DeleteMapping("/delete/{idCmnd}")
    public ResponseEntity<String> supprimerCommande(@PathVariable Long idCmnd) {
        try {
            commandeInterface.supprimerCommande(idCmnd);
            return ResponseEntity.ok("Commande supprimée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Commande non trouvée");
        }
    }


    /// Endpoint pour modifier une commande
    @PutMapping("/modifier/{idCmnd}/{idTable}")
    public ResponseEntity<Map<String, Object>> modifierCommande(
            @PathVariable Long idCmnd,
            @PathVariable Long idTable,  // Ajout de tableId
            @RequestBody List<Long> platIds
    ) {
        try {
            Map<String, Object> response = commandeInterface.modifierCommande(idCmnd, idTable, platIds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }



    @GetMapping("/commandes")
    public ResponseEntity<List<Map<String, Object>>> getAllCommandes() {
        try {
            List<Map<String, Object>> response = commandeInterface.getAllCommandes();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(Collections.singletonMap("error", e.getMessage())));
        }
    }

    @GetMapping("/commande/{idCmnd}")
    public ResponseEntity<Map<String, Object>> getCommandeById(@PathVariable Long idCmnd) {
        try {
            Map<String, Object> commande = commandeInterface.getCommandeById(idCmnd); // Appel au service pour récupérer la commande
            return ResponseEntity.ok(commande); // Retourner la commande avec les informations
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Gérer l'erreur si la commande n'est pas trouvée
        }
    }














}
