import {config, appFetch} from './appFetch';

export const makeBid = (anuncio, onSuccess, onErrors) =>
    appFetch('/bids/makeBid',config('POST', anuncio), onSuccess, onErrors);

export const findUserBids = (page,onSuccess) => {
    let path = `/bids/findBids?page=${page}`;
    appFetch(path,config('GET'), onSuccess);
}