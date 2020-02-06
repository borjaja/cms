import {config, appFetch} from './appFetch';

export const findAllCategories = (onSuccess) => 
    appFetch('/catalog/categories', config('GET'), onSuccess);

export const findProducts = ({categoryId, keywords, page}, 
    onSuccess) => {

    let path = `/catalog/products?page=${page}`;

    path += categoryId ? `&categoryId=${categoryId}` : "";
    path += keywords.length > 0 ? `&keywords=${keywords}` : "";

    appFetch(path, config('GET'), onSuccess);

}

export const findByProductId = (id, onSuccess, onError) => 
    appFetch(`/catalog/products/${id}`, config('GET'), onSuccess, onError);

export const advertise = (anuncio, onSuccess, onErrors) =>
    appFetch('/catalog/advertise',config('POST', anuncio), onSuccess, onErrors);

export const findUserProducts = (page,onSuccess) => {
    let path = `/catalog/userProducts?page=${page}`;
    appFetch(path,config('GET'), onSuccess);
}