/*
 * Project home: taxe-ca
 * 
 * author amtoure.
 * Template pack-angular:src/main/java/domain/Enum.java.enum.vm
 */
package com.app.taxeca.domain.models.enumeration;

public enum Typearticle {

	NON_TAXABLE(0.0), TAXE_10(0.10), TAXE_20(0.20), TAXE_5(0.05);

	private final Double value;

	Typearticle(Double value) {

		this.value = value;
	}

	public Double getValue() {
		return value;
	}

}