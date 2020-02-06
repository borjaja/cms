import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    productSearch: null,
    product: null,
    userProductSearch : null
};

const categories = (state = initialState.categories, action) => {

    switch (action.type) {

        case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
            return action.categories;

        default:
            return state;

    }

}

const productSearch = (state = initialState.productSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCT_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }

}

const product = (state = initialState.product, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCT_BY_ID_COMPLETED:
            return action.product;

        case actionTypes.CLEAR_PRODUCT:
            return initialState.product;

        case actionTypes.UPDATE_PRODUCT_COMPLETED:
            return {...state, hasWinner: true, remainingTime:  action.product.remainingTime, actualPrice: action.product.actualPrice};

        case actionTypes.FIND_PRODUCT_BY_ID_NOT_FOUND:
            return {...state, error:true}
        default:
            return state;

    }

}

const userProductSearch = (state = initialState.userProductSearch, action) => {

    switch (action.type) {
        case actionTypes.FIND_USER_PRODUCTS_COMPLETED:
            return action.userProductSearch;
        
        case actionTypes.CLEAR_USER_PRODUCT_SEARCH:
            return initialState.userProductSearch;
            
        default:
            return state;
    }
}

const reducer = combineReducers({
    categories,
    productSearch,
    product,
    userProductSearch
});

export default reducer;


