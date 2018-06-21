package com.app.taxeca.infrastructure.representation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.taxeca.domain.models.Commande;
import com.app.taxeca.domain.models.LigneCommande;
import com.app.taxeca.domain.models.execption.IllegalCommandeException;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * * Exemple modèle de reprensentation du models pour l(api rest.
 * 
 */
public class CommandeRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Long id;
	@JsonProperty
	private LocalDate commandeDate;
	@JsonProperty
	private Set<LigneCommandeRepresentation> ligneCommandeRepresentation = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCommandeDate() {
		return commandeDate;
	}

	public void setCommandeDate(LocalDate commandeDate) {
		this.commandeDate = commandeDate;
	}

	public CommandeRepresentation() {
		// pour Jackson
	}

	public CommandeRepresentation(Commande commande) {
		this.commandeDate = commande.getCommandeDate();
		Set<LigneCommande> ligneCommandes = commande.getLigneCommandes();

		Set<LigneCommandeRepresentation> ligneCommandeRepresentation = ligneCommandes.stream()
				.map(this::ligneCommandeToRepresentation).collect(Collectors.toSet());

		this.ligneCommandeRepresentation = ligneCommandeRepresentation;
	}

	public LigneCommandeRepresentation ligneCommandeToRepresentation(LigneCommande ligneCommande) {

		if (ligneCommande == null) {
			return null;
		}

		LigneCommandeRepresentation dto = new LigneCommandeRepresentation(ligneCommande);

		return dto;
	}

	public LigneCommande ligneCommandeToEntity(LigneCommandeRepresentation ligneCommandeRepresentation)
			throws IllegalCommandeException {

		if (ligneCommandeRepresentation == null) {
			return null;
		}
		LigneCommande.Builder builder = LigneCommande.builder();
		ArticleRepresentation articleRepresentation = ligneCommandeRepresentation.getArticleRepresentation();

		LigneCommande dto = articleRepresentation.populateAdresse(builder);

		return dto;
	}

	public Commande toCommande() {
		Commande.Builder builder = Commande.builder();
		Commande commande = builder.build();

		ligneCommandeRepresentation.stream().map(ligneitem -> {
			try {
				commande.ajouterArticleCommande(ligneCommandeToEntity(ligneitem));

				// return ligneCommandeToEntity(ligneitem);
			} catch (IllegalCommandeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toSet());

		return builder.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CommandeRepresentation commandeDTO = (CommandeRepresentation) o;
		if (commandeDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), commandeDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
