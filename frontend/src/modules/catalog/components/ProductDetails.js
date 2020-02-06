import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';
import {MakeBid} from '../../bid';

const initialState = {
    error: null
};

class ProductDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = initialState;
    }

    componentDidMount() {
        const id = Number(this.props.match.params.id);
        if (!Number.isNaN(id)) {
            this.props.findProductById(id);
        }
    }

    componentWillUnmount() {
        this.props.clearProduct();
    }

    render() {
        
        const product = this.props.product;
        const user = this.props.user;

        if (!product) 
            return null;

        if (product.error){
                return (
                    <div className="row justify-content-md-center">
                        <div className="jumbotron">
                            <h2>
                                <FormattedMessage id='project.common.ErrorDialog.notFound'/>
                            </h2>
                            <hr className="my-2"></hr>
                            <p>
                                <FormattedMessage id='project.common.ErrorDialog.notFoundMsg'/>
                            </p>
                        </div>
                    </div>
                );
        }
        
        return (

            <div>
                <div className="row">
                <BackLink/>
                </div>

                <div className="row">
                    <div className="col-lg-7 mb-4 card">
                        <div className="form-horizontal card-body">
                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label">
                                <FormattedMessage id='project.catalog.Advertise.productName'/>
                            </label>
                            <div className="col-sm-8 col-md-9 font-weight-bold card-title">{product.name}</div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.category'/>
                            </label>
                            <div className="col-sm-8 col-md-9">
                                {selectors.getCategoryName(this.props.categories, product.category)}
                            </div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.catalog.Advertise.description'/>
                            </label>
                            <div className="col-sm-8 col-md-9">{product.description}</div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.price'/>
                            </label>
                            <div className="form-group col-sm-8 col-md-9 card-text">
                                <FormattedNumber value={product.price}/>€
                            </div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.pseudonimo'/>
                            </label>
                            <div className="col-sm-8 col-md-9 card-text">{product.vendedor}</div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.date'/>
                            </label>
                            <div className="form-group col-sm-8 col-md-9 card-text">
                                <FormattedTime value={product.date}/>&nbsp;-&nbsp;<FormattedDate value={product.date}/>
                            </div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.remainingTime'/>
                            </label>
                            <div className="col-sm-8 col-md-9 card-text">
                                {product.remainingTime < 0 ? 
                                    <FormattedMessage id='project.catalog.Advertise.endBid'/>
                                    : product.remainingTime }
                            </div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.actualPrice'/>
                            </label>
                            <div className="col-sm-8 col-md-9 card-text">
                                {product.actualPrice}&nbsp;€
                            </div>
                            </div>

                            <div className="form-group row">
                            <label className="col-sm-3 col-md-3 form-control-label ">
                                <FormattedMessage id='project.global.fields.deliverInfo'/>
                            </label>
                            <div className="col-sm-8 col-md-9 card-text">
                                {product.deliverInfo}
                            </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-4 offset-lg-1">
                        {this.props.loggedIn && user!==product.vendedor &&
                            <MakeBid product={product}/>}
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => ({
    user: users.selectors.getUser(state),
    loggedIn: users.selectors.isLoggedIn(state),
    product: selectors.getProduct(state),
    categories: selectors.getCategories(state),
});

const mapDispatchToProps = {
    findProductById: actions.findProductById,
    clearProduct: actions.clearProduct
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetails);