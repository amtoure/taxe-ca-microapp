package com.app.taxeca.application.handler;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.taxeca.domain.models.Commande;
import com.app.taxeca.domain.repository.CommandeRepository;

/**
 * Exemple de service applicatif.
 */
@Component
public class CommandeHandler {

	@Autowired
	private CommandeRepository commandeRepository;

	@Transactional
	public Commande create(Commande dossier) {
		Commande createdDossier = commandeRepository.save(dossier);
		return createdDossier;
	}

	public Optional<Commande> findById(Long id) {
		Optional<Commande> commande = commandeRepository.findById(id);
		return commande;
	}

	@Transactional
	public void delete(Long id) {
		commandeRepository.deleteById(id);
	}
}
