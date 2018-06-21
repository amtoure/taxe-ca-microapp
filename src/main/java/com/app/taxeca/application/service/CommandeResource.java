package com.app.taxeca.application.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.taxeca.application.handler.CommandeHandler;
import com.app.taxeca.domain.models.Commande;
import com.app.taxeca.infrastructure.representation.CommandeRepresentation;

@RestController
@RequestMapping(value = "/api/commandes")
public class CommandeResource {
	@Autowired
	private CommandeHandler commandeHandler;

	/**
	 * Exemple de service exposition rest .
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CommandeRepresentation> findById(@PathVariable Long id) {
		Optional<Commande> commande = commandeHandler.findById(id);

		return ResponseEntity.ok().body(new CommandeRepresentation(commande.get()));
	}

	@PostMapping
	public CommandeRepresentation create(@RequestBody @Valid CommandeRepresentation commandeRepresentation) {
		return new CommandeRepresentation(commandeHandler.create(commandeRepresentation.toCommande()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		commandeHandler.delete(id);
	}
}
