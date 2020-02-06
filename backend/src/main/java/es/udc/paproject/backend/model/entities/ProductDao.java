package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, Long>, CustomizedProductDao{
	
//	@Query("SELECT p FROM Product p WHERE p.user.id = ?1 AND p.endDate > ?2 ORDER BY p.endDate DESC")
//	Slice<Product> findProducts(Long userId, LocalDateTime date, Pageable pageable);
	Slice<Product> findByUserIdOrderByEndDateDesc(Long userId, Pageable pageable);
}

