package com.app.taxeca.domain.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.app.taxeca.domain.models.enumeration.Typearticle;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "article_designation")
	private String articleDesignation;

	@Column(name = "article_importe")
	private Boolean articleImporte;

	@Enumerated(EnumType.STRING)
	@Column(name = "typearticle")
	private Typearticle typearticle;

	@Column(name = "article_prix_vente")
	private Double articlePrixVente;

	Article() {
		// Pour Hibernate

	}

	public Article(String articleDesignation, Boolean articleImporte, Typearticle typearticle,
			Double articlePrixVente) {
		super();
		this.articleDesignation = articleDesignation;
		this.articleImporte = articleImporte;
		this.typearticle = typearticle;
		this.articlePrixVente = articlePrixVente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleDesignation() {
		return articleDesignation;
	}

	public Boolean isArticleImporte() {
		return articleImporte;
	}

	public Typearticle getTypearticle() {
		return typearticle;
	}

	public Double getArticlePrixVente() {
		return articlePrixVente;
	}

	public static Builder builder() {
		return new Article.Builder();
	}

	public static class Builder {
		private Article instance = new Article();

		private Builder() {
		}

		public Builder articleDesignation(String articleDesignation) {
			instance.articleDesignation = articleDesignation;
			return this;
		}

		public Builder articleImporte(Boolean articleImporte) {
			instance.articleImporte = articleImporte;
			return this;
		}

		public Builder typearticle(Typearticle typearticle) {
			instance.typearticle = typearticle;
			return this;
		}

		public Builder articlePrixVente(Double articlePrixVente) {
			instance.articlePrixVente = articlePrixVente;
			return this;
		}

		public Article build() {

			return instance;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Article article = (Article) o;
		if (article.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), article.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Article{" + "id=" + getId() + ", articleDesignation='" + getArticleDesignation() + "'"
				+ ", articleImporte='" + isArticleImporte() + "'" + ", typearticle='" + getTypearticle() + "'"
				+ ", articlePrixVente=" + getArticlePrixVente() + "}";
	}
}
