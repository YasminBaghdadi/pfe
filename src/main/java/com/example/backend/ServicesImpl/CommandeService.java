package com.example.backend.ServicesImpl;

import com.example.backend.Entity.Commande;
import com.example.backend.Entity.Plat;
import com.example.backend.Entity.Tab;
import com.example.backend.Entity.User;
import com.example.backend.Repository.CommandeRepository;
import com.example.backend.Repository.PlatRepository;
import com.example.backend.Repository.TabRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.CommandeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeService implements CommandeInterface {
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlatRepository platRepository;
    @Autowired
    TabRepository tabRepository;

    public Map<String, Object> passerCommande(Long userId, Long tableId, List<Long> platIds) {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si la table existe
        Tab table = tabRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table non trouvée"));

        // Récupérer la liste des plats commandés
        List<Plat> plats = platRepository.findAllById(platIds);
        List<String> nomPlats = plats.stream()
                .map(Plat::getName)
                .collect(Collectors.toList());

        // Créer et configurer la commande
        Commande commande = new Commande();
        commande.setUser(user);
        commande.setTab(table);  // Associer la commande à la table
        commande.setPlats(plats);
        commande.calculerTotal();

        // Sauvegarder la commande
        commandeRepository.save(commande);

        // Construire la réponse
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("username", user.getUsername());
        response.put("firstname", user.getFirstname());
        response.put("lastname", user.getLastname());
        response.put("tableNumber", table.getNumber());  // Ajouter le numéro de la table
        response.put("plats", nomPlats);
        response.put("total", commande.getTotal());

        return response;
    }

    @Override
    public void supprimerCommande(Long idCmnd) {
        // Vérifier si la commande existe
        Commande commande = commandeRepository.findById(idCmnd)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Supprimer les associations dans la table de jointure si nécessaire
        commande.getPlats().clear(); // Supprimer l'association plats -> commande

        // Supprimer la commande de la base de données
        commandeRepository.delete(commande);
    }


    @Override
    public Map<String, Object> modifierCommande(Long idCmnd, Long idTable, List<Long> platIds) {
        // Récupérer la commande existante
        Commande commande = commandeRepository.findById(idCmnd)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Vérifier si la table existe
        Tab tab = tabRepository.findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table non trouvée"));

        // Récupérer les plats avec les ids
        List<Plat> plats = platRepository.findAllById(platIds);
        List<String> nomPlats = plats.stream()
                .map(Plat::getName)
                .collect(Collectors.toList());

        // Mettre à jour la table et la liste des plats dans la commande
        commande.setTab(tab);
        commande.setPlats(plats);

        // Recalculer le total de la commande
        commande.calculerTotal();

        // Sauvegarder la commande mise à jour
        commandeRepository.save(commande);

        // Construire la réponse
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("username", commande.getUser().getUsername());
        response.put("firstname", commande.getUser().getFirstname());
        response.put("lastname", commande.getUser().getLastname());
        response.put("idTable", tab.getIdTable());  // Ajout de l'ID de la table
        response.put("tableNumber", tab.getNumber());  // Ajout du numéro de la table
        response.put("plats", nomPlats);
        response.put("total", commande.getTotal());

        return response;
    }


    public List<Map<String, Object>> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Commande commande : commandes) {
            List<String> nomPlats = commande.getPlats().stream()
                    .map(Plat::getName)
                    .collect(Collectors.toList());

            Map<String, Object> commandeResponse = new LinkedHashMap<>();

            // ID de la commande
            commandeResponse.put("id", commande.getIdCmnd());

            // Informations de l'utilisateur
            commandeResponse.put("username", commande.getUser().getUsername());
            commandeResponse.put("firstname", commande.getUser().getFirstname());
            commandeResponse.put("lastname", commande.getUser().getLastname());

            // Informations de la table associée
            commandeResponse.put("numeroTable", commande.getTab().getNumber());

            // Informations des plats
            commandeResponse.put("plats", nomPlats);

            // Total de la commande
            commandeResponse.put("total", commande.getTotal());

            response.add(commandeResponse);
        }

        return response;
    }

    public Map<String, Object> getCommandeById(Long idCmnd) {
        Commande commande = commandeRepository.findById(idCmnd)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        List<String> nomPlats = commande.getPlats().stream()
                .map(Plat::getName)
                .collect(Collectors.toList());

        Map<String, Object> commandeResponse = new LinkedHashMap<>();

        // ID de la commande
        commandeResponse.put("id", commande.getIdCmnd());

        // Informations de l'utilisateur
        commandeResponse.put("username", commande.getUser().getUsername());
        commandeResponse.put("firstname", commande.getUser().getFirstname());
        commandeResponse.put("lastname", commande.getUser().getLastname());

        // Informations de la table associée
        commandeResponse.put("numeroTable", commande.getTab().getNumber());

        // Informations des plats
        commandeResponse.put("plats", nomPlats);

        // Total de la commande
        commandeResponse.put("total", commande.getTotal());

        return commandeResponse;
    }
}