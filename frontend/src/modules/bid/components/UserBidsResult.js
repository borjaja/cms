import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import OwnBids from './OwnBids';

const UserBidsResult = ({userBidsSearch, previousFindUserBidsResultPage, nextFindUserBidsResultPage}) => {

    if (!userBidsSearch) {
        return null;
    }

    if (userBidsSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.bid.FindBids.noBidsFound'/>
            </div>
        );
    }
    
    return (

        <div>
            <h2>
                <FormattedMessage id='project.users.UserBids.title'/>
            </h2>

            <OwnBids bids={userBidsSearch.result.items}/>

            <Pager 
                back={{
                    enabled: userBidsSearch.page >= 1,
                    onClick: () => previousFindUserBidsResultPage(userBidsSearch.page)}}
                next={{
                    enabled: userBidsSearch.result.existMoreItems,
                    onClick: () => nextFindUserBidsResultPage(userBidsSearch.page)}}/>
        </div>

    );

}

const mapStateToProps = (state) => ({
    userBidsSearch: selectors.getUserBidsSearch(state)
});

const mapDispatchToProps = {
    previousFindUserBidsResultPage: actions.previousFindUserBidsResultPage,
    nextFindUserBidsResultPage: actions.nextFindUserBidsResultPage
}

export default connect(mapStateToProps, mapDispatchToProps)(UserBidsResult);