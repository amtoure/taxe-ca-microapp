package com.app.taxeca.domain.models;

import java.io.Serializable;

/**
 * Classe entity permettant de typer explicitement les entités qui
 * l'implémentent
 * 
 *
 * @param <T>
 */
public interface Entite<T> extends Serializable {

	/**
	 * retourne l'identité de l'entité
	 * 
	 * @return identité
	 */
	abstract T getId();

}
