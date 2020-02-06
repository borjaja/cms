package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class BidSummaryDto {

    private Long id;
    private Long idProduct;
    private String name;
    private long date;
    private BigDecimal price;
    private boolean win;
    private boolean end;
    private Long remainingTime;
    private BigDecimal actualPrice;

    public BidSummaryDto() {}

    public BidSummaryDto(Long id, Long idProduct, long date, BigDecimal price, boolean win, boolean end,Long remainingTime,BigDecimal actualPrice,String name) {
        this.id = id;
        this.idProduct = idProduct;
        this.date = date;
        this.price = price;
        this.win = win;
        this.end = end;
        this.remainingTime=remainingTime;
        this.actualPrice=actualPrice;
        this.name=name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
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
}
