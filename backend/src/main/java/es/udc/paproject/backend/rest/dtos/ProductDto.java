package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductDto {
	
	private Long id;
    private String name;
    private String description;
    private String deliverInfo;
    private String vendedor;	//Pseudonimo del vendedor (userName)
    private Long date;
    private Long remainingTime;
    private BigDecimal price;	//Precio de salida
    private BigDecimal actualPrice;
    private Long category; //id de la categoria
    private boolean hasWinner;
    //private BidDto winner;
    //private List<BidDto> bids;
    
    public ProductDto(){ }

    public ProductDto(Long id, String deliverInfo, String name, String description, String vendedor, Long date, Long remainingTime, BigDecimal price,
                      BigDecimal actualPrice, Long category, boolean hasWinner) {
        this.setDeliverInfo(deliverInfo);
    	this.setId(id);
    	this.setName(name);
        this.setDescription(description);
        this.setVendedor(vendedor);
        this.setDate(date);
        this.setRemainingTime(remainingTime);
        this.setPrice(price);
        this.setActualPrice(actualPrice);
        this.setCategory(category);
        this.hasWinner=hasWinner;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(String deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

    public boolean isHasWinner() {
        return hasWinner;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

}
