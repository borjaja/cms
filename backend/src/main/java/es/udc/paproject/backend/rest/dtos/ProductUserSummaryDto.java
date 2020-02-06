package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductUserSummaryDto {
	
	private Long id;
    private String name;
    private BigDecimal actualPrice;
    private Long remainingTime;
    private String winnerEmail;
    private Long category;

    public ProductUserSummaryDto() { }

    public ProductUserSummaryDto(Long id, String name,BigDecimal actualPrice,
    		Long remainingTime, String winnerEmail, Long category ) {
        
    	this.setId(id);
    	this.setName(name);
        this.setActualPrice(actualPrice);
        this.setRemainingTime(remainingTime);
        this.setWinnerEmail(winnerEmail);
        this.category=category;
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWinnerEmail() {
		return winnerEmail;
	}

	public void setWinnerEmail(String winnerEmail) {
		this.winnerEmail = winnerEmail;
	}

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
