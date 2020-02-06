package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    
    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories() {
        return toCategoryDtos(catalogService.findAllCategories());
    }
    
    @GetMapping("/products/{id}")
	public ProductDto findProductById(@PathVariable("id") Long id) throws InstanceNotFoundException {
		return toProductDto(catalogService.findProductById(id));
	}

    @GetMapping("/products")
	public BlockDto<ProductPublicSummaryDto> findProducts(
		@RequestParam(required=false) Long categoryId,
		@RequestParam(required=false) String keywords, 
		@RequestParam(defaultValue="0") int page) {
		
		Block<Product> productBlock = catalogService.findProducts(categoryId, keywords, page, 10);
		return new BlockDto<>(toProductPublicSummaryDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }

    // todo: quizas hace falta otro Summary o usar el anterior
    @GetMapping("/userProducts")
   	public BlockDto<ProductUserSummaryDto> showUserProducts(
   		@RequestAttribute Long userId,
   		@RequestParam(defaultValue="0") int page)
        throws InstanceNotFoundException{
   		
   		Block<Product> productBlock = catalogService.showUserProducts(userId, page, 10);
   		return new BlockDto<>(toProductUserSummaryDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }

    @PostMapping("/advertise")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto advertise(@RequestAttribute Long userId, @Validated @RequestBody ProductParamDto p)
            throws InstanceNotFoundException
    {
        return toIdDto(catalogService.advertise(userId,p.getName(),p.getDescription(),
                p.getDuration(),p.getPrice(),p.getCategory(),p.getDeliverInfo()));
    }
	
}
