import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import OwnProducts from './OwnProducts';

const UserProductsResult = ({userProductSearch, previousFindUserProductsResultPage, nextFindUserProductsResultPage}) => {

    if (!userProductSearch) {
        return null;
    }

    if (userProductSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindProductsResult.noProductsFound'/>
            </div>
        );
    }
    
    return (

        <div>
            <h2>
                <FormattedMessage id='project.catalog.UserProducts.title'/>
            </h2>
            <OwnProducts products={userProductSearch.result.items}/>
            <Pager 
                back={{
                    enabled: userProductSearch.page >= 1,
                    onClick: () => previousFindUserProductsResultPage(userProductSearch.page)}}
                next={{
                    enabled: userProductSearch.result.existMoreItems,
                    onClick: () => nextFindUserProductsResultPage(userProductSearch.page)}}/>
        </div>

    );

}

const mapStateToProps = (state) => ({
    userProductSearch: selectors.getUserProductSearch(state)
});

const mapDispatchToProps = {
    previousFindUserProductsResultPage: actions.previousFindUserProductsResultPage,
    nextFindUserProductsResultPage: actions.nextFindUserProductsResultPage
}

export default connect(mapStateToProps, mapDispatchToProps)(UserProductsResult);