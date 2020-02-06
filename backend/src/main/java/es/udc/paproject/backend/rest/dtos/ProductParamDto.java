package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductParamDto {

    private String name;
    private String description;
    private String deliverInfo;
    private int duration;
    private BigDecimal price;	//Precio de salida
    private Long categoryId;


    public ProductParamDto(){ }

    public ProductParamDto(String name, String description, int duration, BigDecimal price, Long category) {
        this.setName(name);
        this.setDescription(description);
        this.setDuration(duration);
        this.setPrice(price);
        this.setCategory(category);
    }

    @NotNull
    @Size(max = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    @NotNull
    @Size(max = 500)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    @NotNull
    @Min(value = 1)
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

    @NotNull
    @DecimalMin("0.01")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    @NotNull
	public Long getCategory() {
		return categoryId;
	}
	public void setCategory(Long categoryId) {
		this.categoryId = categoryId;
	}

    @NotNull
    @Size(max = 500)
	public String getDeliverInfo() {
		return deliverInfo;
	}
	public void setDeliverInfo(String deliverInfo) {
		this.deliverInfo = deliverInfo;
	}
}
