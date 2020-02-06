package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;

import java.math.BigDecimal;
import java.util.List;


public interface CatalogService {

	Long advertise(Long userId,String name, String description,
			int duration, BigDecimal price, Long category, String deliverInfo)
            throws InstanceNotFoundException;
	
    List<Category> findAllCategories();
	
	Product findProductById(Long id) throws InstanceNotFoundException;
	
	Block<Product> findProducts(Long categoryId, String keywords, int page, int size);

	Block<Product> showUserProducts(Long userId, int page, int size)
            throws InstanceNotFoundException;
}
