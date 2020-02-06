import * as actionTypes from './actionTypes';
import backend from '../../backend';
import catalog from '../catalog';

// todo: esto en la puja no se usa e debería quitar
const makeBidCompleted = makeBid => ({
    type: actionTypes.MAKE_BID_COMPLETED,
    makeBid
});

export const makeBid = (anuncio, onSuccess,onErrors) => dispatch =>
backend.bidService.makeBid(anuncio, 
    result => {
                dispatch(makeBidCompleted(result)); 
                onSuccess();
                dispatch(catalog.actions.updateProduct(result));
              }
                , onErrors);

export const previousFindUserBidsResultPage = page =>
	findUserBids(page-1);

export const nextFindUserBidsResultPage = page =>
	findUserBids(page+1);

export const findUserBids = page => dispatch => {
	dispatch(clearUserBidsSearch())
	backend.bidService.findUserBids(page,
		result => dispatch(findUserBidsCompleted({page,result})));
}

const findUserBidsCompleted = userBidsSearch => ({
    type: actionTypes.FIND_USER_BIDS_COMPLETED,
    userBidsSearch
})

const clearUserBidsSearch = () => ({
	type: actionTypes.CLEAR_USER_BIDS_SEARCH
});