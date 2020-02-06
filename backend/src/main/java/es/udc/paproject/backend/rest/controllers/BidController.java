package es.udc.paproject.backend.rest.controllers;


import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidSummaryDtos;
import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidSummaryDto;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.services.*;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/bids")
public class BidController {
    private final static String BID_AFTER_LIMIT_DATE_EXCEPTION = "project.exceptions.BidAfterLimitDateException";
    private final static String PRICE_UNDER_ACTUAL_PRICE_EXCEPTION = "project.exceptions.PriceUnderActualPriceException";
    private final static String BID_FOR_OWN_PRODUCT_EXCEPTION = "project.exceptions.BidForOwnProductException";

    @Autowired
    private BidService bidService;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BidAfterLimitDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleBidAfterLimitDateException(BidAfterLimitDateException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(BID_AFTER_LIMIT_DATE_EXCEPTION,
                new Object[] {}, BID_AFTER_LIMIT_DATE_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(PriceUnderActualPriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handlePriceUnderActualPriceException(PriceUnderActualPriceException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(PRICE_UNDER_ACTUAL_PRICE_EXCEPTION,
                new Object[] {exception.getMessage()}, PRICE_UNDER_ACTUAL_PRICE_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }
    
    @ExceptionHandler(BidForOwnProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleBidForOwnProductException(BidForOwnProductException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(BID_FOR_OWN_PRODUCT_EXCEPTION,
                new Object[] {}, BID_FOR_OWN_PRODUCT_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("/makeBid")
    public BidSummaryDto makeBid(@RequestAttribute Long userId, @Validated @RequestBody BidParamDto params)
            throws InstanceNotFoundException, BidAfterLimitDateException, PriceUnderActualPriceException, BidForOwnProductException {
        
    	Bid bid = bidService.makeBid(userId,params.getProductId(),params.getPrice());
 
    	return toBidSummaryDto(bid);
    }

    @GetMapping("/findBids")
    public BlockDto<BidSummaryDto> findOrders(@RequestAttribute Long userId, @RequestParam(defaultValue="0") int page)
            throws InstanceNotFoundException{
        Block<Bid> bidBlock = bidService.findBids(userId,page,10);
        return new BlockDto<>(toBidSummaryDtos(bidBlock.getItems()),bidBlock.getExistMoreItems());
    }

}
