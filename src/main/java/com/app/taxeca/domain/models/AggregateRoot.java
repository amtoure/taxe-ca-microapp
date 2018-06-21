package com.app.taxeca.domain.models;

/**
 * Classe AggregateRoot permettant de typer explicitement les agrégats qui
 * l'étendent
 * 
 *
 * 
 * @param <T>
 */
public abstract class AggregateRoot<T> implements Entite<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * retourne le type de l'agrégat
	 * 
	 * @return type
	 */
	public abstract String getType();

}
