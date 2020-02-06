package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PermissionChecker {
	
	void checkUserExists(Long userId)
            throws InstanceNotFoundException;
	
	User checkUser(Long userId)
            throws InstanceNotFoundException;

	Product checkProduct(Long id)
            throws  InstanceNotFoundException;

    Category checkCategory(Long id)
            throws InstanceNotFoundException;

}
