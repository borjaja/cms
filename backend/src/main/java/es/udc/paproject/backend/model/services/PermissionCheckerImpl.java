package es.udc.paproject.backend.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import es.udc.paproject.backend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;

@Service
@Transactional(readOnly=true)
public class PermissionCheckerImpl implements PermissionChecker {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
    private CategoryDao categoryDao;
	
	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {
		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
	}

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		return user.get();
	}

    @Override
    public Product checkProduct(Long id) throws  InstanceNotFoundException{
        Optional<Product> product = productDao.findById(id);
        if (!product.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", id);
        }
        return product.get();
    }

    @Override
    public Category checkCategory(Long id) throws InstanceNotFoundException {
        Optional<Category> category = categoryDao.findById(id);
        if (!category.isPresent()) {
            throw new InstanceNotFoundException("project.entities.category", id);
        }
        return category.get();
    }
}
