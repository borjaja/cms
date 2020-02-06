package es.udc.paproject.backend.model.entities;

import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@BatchSize(size = 10)
@Entity
public class Bid {
	
	private Long id;
	private Product product;
	private User user;
	private LocalDateTime date;
	private BigDecimal price;
	
	public Bid() {}
	
	public Bid(Product product, User user, LocalDateTime date, BigDecimal price) {
		this.product = product;
		this.user = user;
		this.date = date;
		this.price = price;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="productId")
	public Product getProduct() {
		return product;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

	public LocalDateTime getDate() {
		return date;
	}
	public BigDecimal getPrice() {
		return price;
	}

    public void setId(Long id) {
        this.id = id;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
	public void setPrice(BigDecimal price) {
		this.price = price;
	} 
}
