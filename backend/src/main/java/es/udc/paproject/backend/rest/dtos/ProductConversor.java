package es.udc.paproject.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import es.udc.paproject.backend.model.entities.Product;

public class ProductConversor {
	
private ProductConversor() {}
	
	public static ProductDto toProductDto(Product product) {
		
		return new ProductDto(product.getId(),product.getDeliverInfo(),product.getName(), product.getDescription(), product.getUser().getUserName(),
			toMillis(product.getDate()),product.calculateRemainingTime(),product.getPrice(),
			product.getActualPrice(),product.getCategory().getId(),product.getWinner()!=null);
	}
	
	public static List<ProductDto> toProductDtos(List<Product> products) {
		return products.stream().map(ProductConversor::toProductDto).collect(Collectors.toList());
	}

	public static IdDto toIdDto(Long id){
        return new IdDto(id);
    }

    public static ProductPublicSummaryDto toProductPublicSummaryDto(Product product) {
        return new ProductPublicSummaryDto(product.getId(),product.getCategory().getId(),product.getName(),product.getActualPrice(),product.calculateRemainingTime());
    }

    public static List<ProductPublicSummaryDto> toProductPublicSummaryDtos(List<Product> products) {
        return products.stream().map(ProductConversor::toProductPublicSummaryDto).collect(Collectors.toList());
    }

    public static ProductUserSummaryDto toProductUserSummaryDto(Product product) {
        return new ProductUserSummaryDto(product.getId(),product.getName(),product.getActualPrice(),product.calculateRemainingTime(),
                (product.getWinner()==null) ? "No hay ninguna puja": product.getWinner().getUser().getEmail(),product.getCategory().getId());
    }

    public static List<ProductUserSummaryDto> toProductUserSummaryDtos(List<Product> products) {
        return products.stream().map(ProductConversor::toProductUserSummaryDto).collect(Collectors.toList());
    }
	public static long toMillis(LocalDateTime date) {
		return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}
}

