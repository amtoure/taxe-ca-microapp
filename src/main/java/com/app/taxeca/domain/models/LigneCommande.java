package com.app.taxeca.domain.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.app.taxeca.domain.models.enumeration.Typearticle;
import com.app.taxeca.domain.models.execption.IllegalCommandeException;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "ligne_commande")
public class LigneCommande implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "lignecmd_prix")
	private Double lignecmdPrix;

	@Column(name = "lignecmd_tva")
	private Double lignecmdTva;

	@Column(name = "lignecmd_montant_tva")
	private Double lignecmdMontantTva;

	@Column(name = "lignecmd_ttc")
	private Double lignecmdTtc;

	@Column(name = "lignecmd_tva_import")
	private Double lignecmdTvaImport;

	@Column(name = "quantite")
	private Integer quantite;

	@ManyToOne(cascade = CascadeType.ALL)
	private Article article;

	/**
	 * Another side of the same relationship
	 */
	@ManyToOne
	private Commande commande;

	LigneCommande() {
		// Pour Hibernate
	}

	public Long getId() {
		return id;
	}

	public Double getLignecmdPrix() {
		return lignecmdPrix;
	}

	public Double getLignecmdTva() {
		return lignecmdTva;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public Article getArticle() {
		return article;
	}

	private void setArticle(Article article) {
		this.article = article;
	}

	public Commande getCommande() {
		return commande;
	}

	public Double getLignecmdTtc() {
		return lignecmdTtc;
	}

	public Double getLignecmdTvaImport() {
		return lignecmdTvaImport;
	}

	public Double getLignecmdMontantTva() {
		return lignecmdMontantTva;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private LigneCommande instance = new LigneCommande();

		private Builder() {
		}

		public Builder article(String articleDesignation, Boolean articleImporte, Typearticle typearticle,
				Double articlePrixVente, Integer quantite) throws IllegalCommandeException {
			if (articlePrixVente == null || articlePrixVente <= 0) {
				throw new IllegalCommandeException("E01", "Le prix doit etre superieur à zero " + articlePrixVente);
			}

			if (quantite == null || quantite <= 0) {
				throw new IllegalCommandeException("E02", "Le quantité etre superieur à zero " + quantite);
			}
			instance.setArticle(new Article(articleDesignation, articleImporte, typearticle, articlePrixVente));

			instance.quantite = quantite;
			instance.lignecmdPrix = instance.getArticle().getArticlePrixVente();
			instance.lignecmdTva = instance.getArticle().getTypearticle().getValue();
			Double montantHt = instance.lignecmdPrix * instance.quantite;
			Double montantTva = montantHt * instance.lignecmdTva;

			instance.lignecmdTvaImport = 0.0;

			if (instance.getArticle().isArticleImporte() != null && instance.getArticle().isArticleImporte()) {
				instance.lignecmdTvaImport = new Double(Typearticle.TAXE_5.getValue());

				montantTva += montantHt * instance.lignecmdTvaImport;
			}
			instance.lignecmdMontantTva = getMontantArrondiTaxeImporte(montantTva);
			instance.lignecmdTtc = getMontantArrondiTaxeImporte(montantHt + montantTva);
			return this;
		}

		public LigneCommande build() {
			return instance;
		}
	}

	public static Double getMontantArrondiTaxeImporte(Double montant) {
		BigDecimal arrondi = new BigDecimal(Typearticle.TAXE_5.getValue());

		BigDecimal diviserMontant = new BigDecimal(montant).divide(arrondi, 0, RoundingMode.UP);
		BigDecimal resultat = diviserMontant.multiply(arrondi.setScale(2, RoundingMode.HALF_UP));

		return resultat.doubleValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LigneCommande ligneCommande = (LigneCommande) o;
		if (ligneCommande.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), ligneCommande.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		StringBuilder ligneCommande = new StringBuilder();

		if (quantite != null) {
			ligneCommande.append(quantite);
			ligneCommande.append(", ");
		}

		if (article != null) {
			ligneCommande.append(article.getArticleDesignation());
			ligneCommande.append(", ");

			if (article.isArticleImporte() != null && article.isArticleImporte()) {
				ligneCommande.append(" importé(e)");
				ligneCommande.append(", ");
			}
		}

		if (lignecmdPrix != null) {
			ligneCommande.append("à ");

			ligneCommande.append(lignecmdPrix);
			ligneCommande.append("€, ");
		}

		if (lignecmdTtc != null) {
			ligneCommande.append(":");
			ligneCommande.append(lignecmdTtc);
			ligneCommande.append("€ TTC ");

		}

		return ligneCommande.toString();
	}

}
