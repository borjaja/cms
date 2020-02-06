import React from 'react';
import {FormattedMessage, FormattedDate, FormattedTime} from 'react-intl';
import PropTypes from 'prop-types';

import {ProductLink} from '../../common';

const OwnBids = ({bids}) => (

    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.bid.FindBids.product'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.bid.FindBids.time'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.bid.FindBids.date'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.bid.FindBids.bidPrice'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.bid.FindBids.bidState'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {bids.map(bid => 
                <tr key={bid.id}>
                    <td><ProductLink id={bid.idProduct} name={bid.name}/></td>
                    <td><FormattedTime value={bid.date}/></td>
                    <td><FormattedDate value={bid.date}/></td>
                    <td>{bid.price}&nbsp;€</td>

                    {bid.win === true ?
                        bid.end === true ?
                            <td><FormattedMessage id='project.bid.FindBid.winner'/></td>
                            :
                            <td><FormattedMessage id='project.bid.FindBid.winning'/></td>
                        :
                        <td><FormattedMessage id='project.bid.FindBid.loser'/></td>
                    }
                </tr>
            )}
        </tbody>

    </table>

);

OwnBids.propTypes = {
    bids: PropTypes.array.isRequired
};

export default OwnBids;