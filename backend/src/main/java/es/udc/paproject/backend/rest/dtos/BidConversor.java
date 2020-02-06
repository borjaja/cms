package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.entities.Product;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class BidConversor {
    public final static List<BidSummaryDto> toBidSummaryDtos(List<Bid> orders) {
        return orders.stream().map(o -> toBidSummaryDto(o)).collect(Collectors.toList());
    }

    public final static BidSummaryDto toBidSummaryDto(Bid bid) {
    	Product bidProduct = bid.getProduct(); 
        return new BidSummaryDto(bid.getId(), bidProduct.getId(), toMillis(bid.getDate()), bid.getPrice(),
                bidProduct.getWinner().getId()==bid.getId(),bidProduct.calculateRemainingTime()<0,
                bidProduct.calculateRemainingTime(),bidProduct.getActualPrice(),bidProduct.getName());
    }

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
}


