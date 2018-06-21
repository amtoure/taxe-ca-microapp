package com.app.taxeca.infrastructure.representation;

import java.io.Serializable;
import java.util.Objects;

import com.app.taxeca.domain.models.Article;
import com.app.taxeca.domain.models.LigneCommande;
import com.app.taxeca.domain.models.enumeration.Typearticle;
import com.app.taxeca.domain.models.execption.IllegalCommandeException;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exemple modèle de reprensentation du models pour l(api rest.
 */
public class ArticleRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;
	@JsonProperty
	private String articleDesignation;
	@JsonProperty
	private Boolean articleImporte;
	@JsonProperty
	private Typearticle typearticle;
	@JsonProperty
	private Double articlePrixVente;

	@JsonProperty
	private Integer quantite;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleDesignation() {
		return articleDesignation;
	}

	public void setArticleDesignation(String articleDesignation) {
		this.articleDesignation = articleDesignation;
	}

	public Boolean isArticleImporte() {
		return articleImporte;
	}

	public void setArticleImporte(Boolean articleImporte) {
		this.articleImporte = articleImporte;
	}

	public Typearticle getTypearticle() {
		return typearticle;
	}

	public void setTypearticle(Typearticle typearticle) {
		this.typearticle = typearticle;
	}

	public Double getArticlePrixVente() {
		return articlePrixVente;
	}

	public void setArticlePrixVente(Double articlePrixVente) {
		this.articlePrixVente = articlePrixVente;
	}

	public ArticleRepresentation() {
		// pour Jackson
	}

	ArticleRepresentation(Article article) {
		this.id = article.getId();
		this.articleDesignation = article.getArticleDesignation();
		this.articleImporte = article.isArticleImporte();
		this.articlePrixVente = article.getArticlePrixVente();
	}

	public LigneCommande populateAdresse(LigneCommande.Builder builder) throws IllegalCommandeException {
		return builder.article(articleDesignation, articleImporte, typearticle, articlePrixVente, quantite).build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ArticleRepresentation articleDTO = (ArticleRepresentation) o;
		if (articleDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), articleDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
