package com.mexpedition.web.controller;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exceptions.ExpeditionExistanteException;
import com.mexpedition.web.exceptions.ExpeditionImpossibleException;
import com.mexpedition.web.exceptions.ExpeditionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExpeditionController {

    @Autowired
    ExpeditionDao expeditionDao;

    @GetMapping( value = "/suivi/{id}")
    public Optional<Expedition> getExpedition(@PathVariable int id) {

        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent())  throw new ExpeditionNotFoundException("L'expedition à l'id " + id + " n'existe pas");

        return expedition;
    }

    @PutMapping(value = "/suivi/update")
    public void updateCommande(@RequestBody Expedition expedition) {

        expeditionDao.save(expedition);
    }

    /*
     * Opération pour enregistrer un paiement et notifier le microservice commandes pour mettre à jour le statut de la commande en question
     **/
    @PostMapping(value = "/suivi/add")
    public ResponseEntity<Expedition> addState(@RequestBody Expedition expedition){

        Expedition expeditionExistant = expeditionDao.findByidCommande(expedition.getIdCommande());
        if(expeditionExistant != null) throw new ExpeditionExistanteException("Cette commande a déjà un suivi");

        //Enregistrer le paiement
        Expedition newExpedition = expeditionDao.save(expedition);

        // si le DAO nous retourne null c'est que il ya eu un problème lors de l'enregistrement
        if(newExpedition == null) throw new ExpeditionImpossibleException("Erreur, impossible d'ajouter un état de suivi, réessayez plus tard");

        return new ResponseEntity<Expedition>(newExpedition, HttpStatus.CREATED);

    }

}

