const getModuleState = state => state.catalog;
export const getProduct = state =>
    getModuleState(state).product;

export const getUserBidsSearch = state =>
    state.bid.userBidsSearch;

export const getBid = state =>
    state.bid.makeBid;