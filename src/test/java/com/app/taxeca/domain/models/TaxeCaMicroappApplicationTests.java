package com.app.taxeca.domain.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.taxeca.domain.models.Commande;
import com.app.taxeca.domain.models.LigneCommande;
import com.app.taxeca.domain.models.enumeration.Typearticle;
import com.app.taxeca.domain.models.execption.IllegalCommandeException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxeCaMicroappApplicationTests {

	private final Logger log = LoggerFactory.getLogger(TaxeCaMicroappApplicationTests.class);

	@Test
	public void test_creationcCommande_et_calcul_montantTtc_et_montantTaxe() throws IllegalCommandeException {
		Commande comande = Commande.builder().build();
		LigneCommande ligneCommande = LigneCommande.builder().article("livres", false, Typearticle.TAXE_10, 12.49, 2)
				.build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder().article("CD musical", false, Typearticle.TAXE_20, 14.99, 1).build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder().article("barres de chocolat", false, Typearticle.NON_TAXABLE, 0.85, 3)
				.build();
		comande.ajouterArticleCommande(ligneCommande);

		assertEquals(new Double(5.5), comande.getTotalMontantTaxe());
		assertEquals(new Double(48.05), comande.getTotalMontantTtc());
		log.info(comande.imrpimerFacture());

		comande = Commande.builder().build();
		ligneCommande = LigneCommande.builder().article("boîtes de chocolats", true, Typearticle.NON_TAXABLE, 10.0, 2)
				.build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder().article("flacons de parfum", true, Typearticle.TAXE_20, 47.50, 3)
				.build();
		comande.ajouterArticleCommande(ligneCommande);

		assertEquals(new Double(36.65), comande.getTotalMontantTaxe());
		assertEquals(new Double(199.15), comande.getTotalMontantTtc());
		log.info(comande.imrpimerFacture());

		comande = Commande.builder().build();

		ligneCommande = LigneCommande.builder().article("flacons de parfum", true, Typearticle.TAXE_20, 27.99, 2)
				.build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder().article("flacons de parfum", false, Typearticle.TAXE_20, 18.99, 1)
				.build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder()
				.article("boîtes de pilules contre la migraine", false, Typearticle.NON_TAXABLE, 9.75, 3).build();
		comande.ajouterArticleCommande(ligneCommande);
		ligneCommande = LigneCommande.builder().article("boîtes de chocolats", true, Typearticle.NON_TAXABLE, 11.25, 2)
				.build();
		comande.ajouterArticleCommande(ligneCommande);

		assertEquals(new Double(18.95), comande.getTotalMontantTaxe());
		assertEquals(new Double(145.7), comande.getTotalMontantTtc());

		log.info(comande.imrpimerFacture());

	}

	@Test(expected = IllegalCommandeException.class)
	public void test_pour_lever_exception_sur_prix_negatif() throws IllegalCommandeException {
		Commande comande = Commande.builder().build();
		LigneCommande ligneCommande = LigneCommande.builder().article("livres", false, Typearticle.TAXE_10, -12.49, 2)
				.build();
		comande.ajouterArticleCommande(ligneCommande);

	}

	@Test
	public void test_regle_arrondi_cinq_centimes_superieurs() {
		assertEquals(new Double(1.00), LigneCommande.getMontantArrondiTaxeImporte(new Double(0.99)));
		assertEquals(new Double(1.00), LigneCommande.getMontantArrondiTaxeImporte(new Double(1.00)));

		assertEquals(new Double(1.05), LigneCommande.getMontantArrondiTaxeImporte(new Double(1.01)));

		assertEquals(new Double(1.05), LigneCommande.getMontantArrondiTaxeImporte(new Double(1.02)));

	}

}
