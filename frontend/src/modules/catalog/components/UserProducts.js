import React from 'react';
import {connect} from 'react-redux';

import * as actions from '../actions';

class UserProducts extends React.Component {

    componentDidMount() {

        this.props.dispatch(actions.findUserProducts(0));
        this.props.history.push('/catalog/userProductsResult');

    }

    render() {
        return null;
    }

}

export default connect()(UserProducts);