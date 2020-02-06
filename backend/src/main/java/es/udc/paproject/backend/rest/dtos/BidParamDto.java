package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BidParamDto {

    private Long productId;
    private BigDecimal price;

    public BidParamDto() {}

    public BidParamDto(Long productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    @NotNull
    public Long getProductId() {
        return productId;
    }

    @NotNull
    @DecimalMin ("0.0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
