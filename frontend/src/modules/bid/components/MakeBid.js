import React from 'react';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import {Errors,SuccessFormatted} from '../../common';
import $ from 'jquery';
import * as selectors from '../selectors';
import * as actions from '../actions';

const initialState = {
    backendErrors: null,
    backendSuccess: null,
    price: '',
    minbid: ''
};

class MakeBid extends React.Component {

    constructor(props) {

        super(props);

        this.state = initialState
    }

    handlePriceChange(event) {
        this.setState({price: event.target.value});
    }

    handleErrorsClose() {
        this.setState({backendErrors: null});
    }

    handleSuccessClose() {
        this.setState({backendSuccess: null});
    }

    handleSubmit(event){
        event.preventDefault();
        const form = $('#signup-form').get(0);
        
        if (form.checkValidity()) {
            form.classList.remove('was-validated');
            this.makeBid();
        } else {
            form.classList.add('was-validated');
        }
    }

    makeBid(){
        this.props.dispatch(actions.makeBid(
            {productId: this.props.product.id,
                price: this.state.price},
                () => {this.setState({backendSuccess:1})},
                errors => this.setBackendErrors(errors)
        ))
    }

    setBackendErrors(backendErrors) {
        this.setState({backendErrors});
    }

    render() {

        const product = this.props.product;

        return (
            <div>
                    <div className="jumbotron">
                        <form id="signup-form" className="needs-validation" noValidate onSubmit={(e) => this.handleSubmit(e)}>
                            <div className="form-group row">
                                <h2 className="col align-self-center">
                                    <FormattedMessage id="project.bid.MakeBid.header"/>
                                </h2>
                            </div>
                            
                            <div className="form-group row">
                                <label htmlFor="price" className="col-md-4 col-form-label">
                                    <FormattedMessage id="project.bid.MakeBid.price"/>
                                </label>
                                <div className="col-md-8">
                                    <input type="number" id="price" className="form-control"
                                        value={this.state.price}
                                        min = {product.price===product.actualPrice?product.price:product.actualPrice+0.01}
                                        step="any"
                                        onChange={(e) => this.handlePriceChange(e)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="price" className="col-md-12 col-form-label">
                                {!product.hasWinner?
                                    <FormattedMessage id="project.bid.MakeBid.minBidEqual"/>:
                                    <FormattedMessage id="project.bid.MakeBid.minBid"/>}
                                    &nbsp;
                                    {product.actualPrice}&nbsp;€
                                </label>
                            </div>
                            <div className="form-group row">
                                <div className="col">
                                    <button type="submit" className="btn btn-primary btn-lg btn-block">
                                        <FormattedMessage id="project.bid.MakeBid.submit"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                <Errors errors={this.state.backendErrors} onClose={() => this.handleErrorsClose()}/>
                {(this.state.backendSuccess && this.props.bid.win)?
                <SuccessFormatted message="project.bid.MakeBid.bidSuccess.winner" onClose={() => this.handleSuccessClose()}/>
                : ''
                }
                {(this.state.backendSuccess && !this.props.bid.win)?
                <SuccessFormatted message="project.bid.MakeBid.bidSuccess.loser" onClose={() => this.handleSuccessClose()}/>
                : ''
                }
            </div>
               
        );    
    }
}
const mapStateToProps = (state) => ({
    //product: selectors.getProduct(state),
    bid: selectors.getBid(state)
});


export default withRouter(connect(mapStateToProps)(MakeBid));