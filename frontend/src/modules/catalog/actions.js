import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const findAllCategoriesCompleted = categories => ({
    type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
    categories
}); 

export const findAllCategories = () => (dispatch, getState) => {

    const categories = selectors.getCategories(getState());

    if (!categories) {

        backend.catalogService.findAllCategories(
            categories => dispatch(findAllCategoriesCompleted(categories))
        );
    }
}

const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch
});

export const findProducts = criteria => dispatch => {

    dispatch(clearProductSearch());
    backend.catalogService.findProducts(criteria,
        result => dispatch(findProductsCompleted({criteria, result})));

}

export const previousFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page-1});

export const nextFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page+1});
    

const clearProductSearch = () => ({
    type: actionTypes.CLEAR_PRODUCT_SEARCH
});

const findProductByIdCompleted = product => ({
    type: actionTypes.FIND_PRODUCT_BY_ID_COMPLETED,
    product
});

const updateProductCompleted = product =>({
    type: actionTypes.UPDATE_PRODUCT_COMPLETED,
    product
});

const findProductByIdNotFound = () => ({
    type: actionTypes.FIND_PRODUCT_BY_ID_NOT_FOUND
});

export const findProductById = (id) => dispatch => {
    backend.catalogService.findByProductId(id,
        product => dispatch(findProductByIdCompleted(product)),
        error => dispatch(findProductByIdNotFound()));
}

export const clearProduct = () => ({
    type: actionTypes.CLEAR_PRODUCT
});

export const updateProduct = product => dispatch =>{
    dispatch(updateProductCompleted(product));
}

export const advertise = (anuncio, onSuccess, onErrors) => dispatch =>
    backend.catalogService.advertise(anuncio, onSuccess, onErrors);

const findUserProductsCompleted = userProductSearch => ({
    type: actionTypes.FIND_USER_PRODUCTS_COMPLETED,
    userProductSearch
})

export const previousFindUserProductsResultPage = page =>
    findUserProducts(page-1);

export const nextFindUserProductsResultPage = page =>
    findUserProducts(page+1);

export const findUserProducts = page => dispatch => {
    dispatch(clearUserProductSearch())
    backend.catalogService.findUserProducts(page,
        result => dispatch(findUserProductsCompleted({page,result})));
}

const clearUserProductSearch = () => ({
    type: actionTypes.CLEAR_USER_PRODUCT_SEARCH
});
