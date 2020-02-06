package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import es.udc.paproject.backend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BidServiceImpl implements BidService{
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private BidDao bidDao;

//    @Autowired
//    private CategoryDao categoryDao;

    public void checkProductDistinctUser(Product product, Long userId) throws BidForOwnProductException{
		if (product.getUser().getId() == userId) {
			throw new BidForOwnProductException();
		}
	}
    public void checkProductInTime(Product product, LocalDateTime bidDate) throws BidAfterLimitDateException {
        if (bidDate.isAfter(product.getEndDate())){
            throw new BidAfterLimitDateException();
        }
    }


    public void checkProductMinPrice(Product product, BigDecimal price) throws PriceUnderActualPriceException {
        if (price.compareTo(product.getActualPrice()) < 0) {
            throw new PriceUnderActualPriceException(product.getActualPrice());
        }
        if(price.compareTo(product.getActualPrice())==0 && product.getWinner()!=null)
            throw new PriceUnderActualPriceException(product.getActualPrice());
    }
	
	@Override
	public Bid makeBid(Long userId, Long productId, BigDecimal price)
            throws InstanceNotFoundException, BidAfterLimitDateException, PriceUnderActualPriceException, BidForOwnProductException{
	    LocalDateTime dateBid=LocalDateTime.now();
		User user = permissionChecker.checkUser(userId);
		Product product = permissionChecker.checkProduct(productId);
		checkProductDistinctUser(product, userId);
        checkProductInTime(product,dateBid);
        checkProductMinPrice(product,price);
		Bid bid = new Bid(product, user, dateBid, price);
		product.updatePriceByBid(bid);
		
		bidDao.save(bid);
		productDao.save(product);
		return bid;
	}
	
	@Override
	public Block<Bid> findBids(Long userId, int page, int size) throws InstanceNotFoundException{
	    permissionChecker.checkUser(userId);
		Slice<Bid> slice = bidDao.findByUserIdOrderByDateDesc(userId, PageRequest.of(page, size));
		
		return new Block<>(slice.getContent(),slice.hasNext());
	}
	
}

