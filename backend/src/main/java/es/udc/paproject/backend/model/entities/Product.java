package es.udc.paproject.backend.model.entities;

import javax.persistence.*;

import es.udc.paproject.backend.rest.dtos.ProductConversor;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@BatchSize(size = 10)
@Entity
public class Product implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final BigDecimal increase= new BigDecimal(0.5);

    private Long id;
    private String name;
    private String description;
    private String deliverInfo;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private BigDecimal price;
    private BigDecimal actualPrice;
    private Category category;
    private User user;
    private Bid winner;
    private long version;

    public Product(){ }

    public Product(String name, String description, BigDecimal price, Category category, String deliverInfo, LocalDateTime date, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.deliverInfo = deliverInfo;
        this.date = date;
        this.endDate = endDate;
        this.price = price;
        this.actualPrice = price;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id; }

    @ManyToOne(optional=false, fetch= FetchType.LAZY)
    @JoinColumn(name="categoryId")
    public Category getCategory() {
        return category;
    }

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="winnerId")
	public Bid getWinner() {
		return winner;
	}

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public BigDecimal getActualPrice() {
        return actualPrice;
    }
    public String getDeliverInfo() {
        return deliverInfo;
    }
    @Version
    @Column(name="productVersion")
	public long getVersion() {
		return version;
	}

 
    public void setUser(User user) { this.user = user; }
	public void setWinner(Bid winner) { this.winner = winner; }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public void setDeliverInfo(String deliverInfo) {
		this.deliverInfo = deliverInfo;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	
    @Transient
    public void updatePriceByBid(Bid bid) {
        BigDecimal ofert = bid.getPrice();
        if (winner == null) {
            setWinner(bid);
        } else {
            if (winner.getPrice().compareTo(ofert) < 0 && ofert.subtract(winner.getPrice()).compareTo(increase) > 0) {
                actualPrice = winner.getPrice().add(increase);
            } else if (winner.getPrice().compareTo(ofert) < 0 && ofert.subtract(winner.getPrice()).compareTo(increase) < 0) {
                actualPrice = ofert;
            } else if(winner.getPrice().subtract(ofert).compareTo(increase) < 0) {
                actualPrice = winner.getPrice();
            } else
                actualPrice = ofert.add(increase);

            if (getWinner().getPrice().compareTo(ofert) < 0) {
                setWinner(bid);
            }
        }
    }
    
//    @Transient
//    public static long toMillis(LocalDateTime date) {
//		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
//	}
    
    
    public long calculateRemainingTime() {
    	return (ProductConversor.toMillis(this.getEndDate())-ProductConversor.toMillis(LocalDateTime.now()))/60000;
    }
    //Llama a REST por toMillis!!


    
    
    
    
    
    
    
    
}

