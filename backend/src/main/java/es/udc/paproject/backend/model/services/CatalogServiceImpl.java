package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PermissionChecker check;

	@Override
	public Long advertise(Long userId, String name, String description, int duration, BigDecimal price,
			Long category, String deliverInfo) throws InstanceNotFoundException {
        User propietario = check.checkUser(userId);
        Category cat = check.checkCategory(category);
        LocalDateTime time= LocalDateTime.now();
		Product product = new Product(name,description,price,cat,deliverInfo,time,time.plusMinutes(duration));
		product.setUser(propietario);
		productDao.save(product);
		return product.getId();
	}

    @Override
    public List<Category> findAllCategories() {
        Iterable<Category> categories = categoryDao.findAll(new Sort(Sort.Direction.ASC, "name"));
        List<Category> categoriesAsList = new ArrayList<>();
        categories.forEach(c -> categoriesAsList.add(c));
        return categoriesAsList;
    }

	@Override
	public Product findProductById(Long id) throws InstanceNotFoundException {
        return check.checkProduct(id);
	}

	@Override
	public Block<Product> findProducts(Long categoryId, String keywords, int page, int size) {
        Slice<Product> slice = productDao.find(categoryId, keywords, page, size);
        return new Block<>(slice.getContent(), slice.hasNext());
	}

	@Override
	public Block<Product> showUserProducts(Long userId, int page, int size) throws InstanceNotFoundException {
	    	check.checkUser(userId);
	    Slice<Product> slice = productDao.findByUserIdOrderByEndDateDesc(userId, PageRequest.of(page, size));
        //Slice<Product> slice = productDao.findProducts(userId,LocalDateTime.now(), PageRequest.of(page, size));
        return new Block<>(slice.getContent(), slice.hasNext());
	}

}
