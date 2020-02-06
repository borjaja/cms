package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class PriceUnderActualPriceException extends Exception {
	
	public PriceUnderActualPriceException(BigDecimal price) {
		super(price.toString());
	}
}
