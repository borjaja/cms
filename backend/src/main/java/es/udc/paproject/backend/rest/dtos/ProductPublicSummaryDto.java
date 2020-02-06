package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductPublicSummaryDto {
	
	private Long id;
	private Long category;
    private String name;
    private BigDecimal actualPrice;
    private Long remainingTime;
    
    public ProductPublicSummaryDto() {}

    public ProductPublicSummaryDto(Long id,Long category, String name, BigDecimal actualPrice, Long remainingTime ) {
    	this.setId(id);
    	this.setCategoryName(category);
    	this.setName(name);
        this.setActualPrice(actualPrice);
        this.setRemainingTime(remainingTime);
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

	public Long getCategory() {
		return category;
	}

	public void setCategoryName(Long category) {
		this.category = category;
	}
}
