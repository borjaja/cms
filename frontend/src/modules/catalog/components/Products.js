import React from 'react';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import * as selectors from '../selectors';
import {ProductLink} from '../../common';

const Products = ({products, categories}) => (

    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.category'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.name'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.actualPrice'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.remainingTime'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {products.map(product => 
                <tr key={product.id}>
                    <td>{selectors.getCategoryName(categories, product.category)}</td>
                    <td><ProductLink id={product.id} name={product.name}/></td>
                    <td>{product.actualPrice}&nbsp;€</td>
                    <td>{product.remainingTime}&nbsp;min.</td>
                </tr>
            )}
        </tbody>

    </table>

);

Products.propTypes = {
    products: PropTypes.array.isRequired,
    categories: PropTypes.array.isRequired
};

export default Products;