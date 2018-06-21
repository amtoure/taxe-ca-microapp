package com.app.taxeca.domain.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
public class Commande extends AggregateRoot<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@CreatedDate
	@Column(name = "commande_date")
	private LocalDate commandeDate;

	/**
	 * A relationship
	 */
	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LigneCommande> ligneCommandes = new HashSet<>();

	Commande() {
		// Pour Hibernate
	}

	public Long getId() {
		return id;
	}

	public LocalDate getCommandeDate() {
		return commandeDate;
	}

	public Set<LigneCommande> getLigneCommandes() {
		return ligneCommandes;
	}

	public Double getTotalMontantTtc() {

		return ligneCommandes.stream().//
				mapToDouble(LigneCommande::getLignecmdTtc).sum();

	}

	public Double getTotalMontantTaxe() {

		return ligneCommandes.stream().//
				mapToDouble(LigneCommande::getLignecmdMontantTva).sum();

	}

	public Commande ajouterArticleCommande(LigneCommande ligneCommande) {
		this.ligneCommandes.add(ligneCommande);

		return this;
	}

	public String imrpimerFacture() {
		return new StringJoiner("\n")
				.add(this.ligneCommandes.stream().map(LigneCommande::toString).collect(Collectors.joining("\n")))
				.add("Montant des taxes : " + getTotalMontantTaxe()).add("Total : " + getTotalMontantTtc()).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Commande commande = (Commande) o;
		if (commande.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), commande.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Commande{" + "id=" + getId() + ", commandeDate='" + getCommandeDate() + "'" + "}";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Commande instance = new Commande();

		private Builder() {
		}

		public Commande build() {
			return instance;
		}
	}

	@Override
	public String getType() {
		return Commande.class.getName();
	}
}
