import React from 'react';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import {ProductLink} from '../../common';

const OwnProducts = ({products}) => (

    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.name'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.actualPrice'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.remainingTime'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.winnerEmail'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {products.map(product => 
                <tr key={product.id}>
                    <td><ProductLink id={product.id} name={product.name}/></td>
                    <td>{product.actualPrice}</td>
                    {product.remainingTime > 0 ?
                        <td>{product.remainingTime}&nbsp;min.</td>
                        :
                        <td><FormattedMessage id='project.catalog.Advertise.endBid'/></td>
                    }
                    <td>{product.winnerEmail}</td>
                </tr>
            )}
        </tbody>

    </table>

);

OwnProducts.propTypes = {
    products: PropTypes.array.isRequired,
    categories: PropTypes.array.isRequired
};

export default OwnProducts;