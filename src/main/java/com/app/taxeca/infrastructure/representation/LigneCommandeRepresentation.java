package com.app.taxeca.infrastructure.representation;

import java.io.Serializable;
import java.util.Objects;

import com.app.taxeca.domain.models.LigneCommande;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * * Exemple modèle de reprensentation du models pour l(api rest. .
 */
public class LigneCommandeRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Long id;
	@JsonProperty
	private Double lignecmdPrix;
	@JsonProperty
	private Double lignecmdTva;
	@JsonProperty
	private Integer quantite;
	@JsonProperty
	private ArticleRepresentation articleRepresentation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLignecmdPrix() {
		return lignecmdPrix;
	}

	public void setLignecmdPrix(Double lignecmdPrix) {
		this.lignecmdPrix = lignecmdPrix;
	}

	public Double getLignecmdTva() {
		return lignecmdTva;
	}

	public void setLignecmdTva(Double lignecmdTva) {
		this.lignecmdTva = lignecmdTva;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public ArticleRepresentation getArticleRepresentation() {
		return articleRepresentation;
	}

	public void setArticleRepresentation(ArticleRepresentation articleRepresentation) {
		this.articleRepresentation = articleRepresentation;
	}

	LigneCommandeRepresentation() {
		// pour Jackson
	}

	public LigneCommandeRepresentation(LigneCommande ligneCommande) {
		this.id = ligneCommande.getId();
		this.articleRepresentation = new ArticleRepresentation(ligneCommande.getArticle());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		LigneCommandeRepresentation ligneCommandeDTO = (LigneCommandeRepresentation) o;
		if (ligneCommandeDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), ligneCommandeDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
