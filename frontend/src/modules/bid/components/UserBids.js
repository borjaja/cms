import React from 'react';
import {connect} from 'react-redux';

import * as actions from '../actions';

class UserBids extends React.Component {

    componentDidMount() {

        this.props.dispatch(actions.findUserBids(0));
        this.props.history.push('/bids/userBidsResult');

    }

    render() {
        return null;
    }

}

export default connect()(UserBids);