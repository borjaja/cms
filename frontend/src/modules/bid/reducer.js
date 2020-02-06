import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    makeBid: null,    
    userBidsSearch: null
};

const makeBid = (state = initialState.makeBid, action) => {

    switch (action.type) {

        case actionTypes.MAKE_BID_COMPLETED:
            return action.makeBid;

        default:
            return state;

    }
}

const userBidsSearch = (state = initialState.userBidsSearch, action) => {

    switch (action.type) {
        case actionTypes.FIND_USER_BIDS_COMPLETED:
            return action.userBidsSearch;
        
        case actionTypes.CLEAR_USER_BIDS_SEARCH:
            return initialState.userBidsSearch;
            
        default:
            return state;
    }
}

const reducer = combineReducers({
    makeBid,
    userBidsSearch
});

export default reducer;