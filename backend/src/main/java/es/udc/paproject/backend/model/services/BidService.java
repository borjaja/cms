package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;

public interface BidService {
	
	Bid makeBid(Long userId, Long productId, BigDecimal price)
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException,BidForOwnProductException;
	
	Block<Bid> findBids(Long userId, int page, int size)
            throws InstanceNotFoundException;
}
