import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import $ from 'jquery';

import {Errors} from '../../common';
import * as actions from '../actions';
import {CategorySelector} from '../../catalog';

const initialState = {
    id:0,
    productName: '',
    description: '',
    deliverInfo: '',
    duration: 0,
    price: 0,
    categoryId: '',
    backendErrors: null,
    exito: false
};

class Advertise extends React.Component {

    constructor(props) {
        super(props);
        this.state = initialState;
    }

    handleProductNameChange(event) {
        this.setState({productName: event.target.value});
    }

    handleDescriptionChange(event) {
        this.setState({description: event.target.value});
    }

    handleDeliverInfoChange(event) {
        this.setState({deliverInfo: event.target.value});
    }

    handleDurationChange(event) {
        this.setState(this.toNumber(event.target.value) > 0 ? {duration: event.target.value} : {duration: 0});
    }

    handlePriceChange(event) {
        this.setState(this.toNumber(event.target.value) > 0 ? {price: event.target.value} : {price: 0});
    }

    handleCategoryIdChange(event) {
        this.setState({categoryId: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.setState({exito: null}) //para hacer que el mensaje desaparezca cuando se pulsa el boton
        const form = $('#advertise-form').get(0);
        if (form.checkValidity()) {
            this.advertise();
            form.classList.remove('was-validated');
        } else {
            this.setBackendErrors(null);
            form.classList.add('was-validated');
        }
    }

    advertise() {
        this.props.dispatch(actions.advertise(
            {name: this.state.productName.trim(),
            description: this.state.description.trim(),
            deliverInfo: this.state.deliverInfo.trim(),
            duration: this.state.duration,
            price: this.state.price,
            category: this.state.categoryId},
            (state) => {
                this.setState(initialState);
                this.setState({exito: 1});
                this.setState({id: state.id});
            },
            errors => this.setBackendErrors(errors)
        )); 
    }

    toNumber(value) {
        return value.length > 0 ? Number(value) : null;
    }

    setBackendErrors(backendErrors) {
        this.setState({backendErrors});
    }

    handleErrorsClose() {
        this.setState({backendErrors: null});
    }

    handleSuccessClose() {
        this.setState({exito: null});
    }

    render() {

        return (
            <div>
                <Errors errors={this.state.backendErrors} onClose={() => this.handleErrorsClose()}/>
                {this.state.exito ?
                    <div className="alert alert-success alert-dismissible fade show" role="alert">
                        <FormattedMessage id="project.catalog.Advertise.success"/>{this.state.id}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close" 
                            onClick={() => this.handleSuccessClose()}>
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    :
                    ""
                }
                <div className="card bg-light border-dark">
                    <h5 className="card-header">
                        <FormattedMessage id="project.catalog.Advertise.header"/>
                    </h5>
                    <div className="card-body">
                        <form id="advertise-form" className="needs-validation" noValidate onSubmit={(e) => this.handleSubmit(e)}>
                            <div className="form-group row">
                                <label htmlFor="productName" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.productName"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="productName" className="form-control"
                                        value={this.state.productName}
                                        onChange={(e) => this.handleProductNameChange(e)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="description" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.description"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="description" className="form-control"
                                        value={this.state.description}
                                        onChange={(e) => this.handleDescriptionChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="deliverInfo" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.deliverInfo"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="deliverInfo" className="form-control"
                                        value={this.state.deliverInfo}
                                        onChange={(e) => this.handleDeliverInfoChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="duration" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.duration"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="number" id="duration" className="form-control"
                                        value={this.state.duration}
                                        onChange={(e) => this.handleDurationChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="price" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.price"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="number" step="0.01" id="price" className="form-control"
                                        value={this.state.price}
                                        onChange={(e) => this.handlePriceChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.catalog.Advertise.category"/>
                                </label>
                                <div className="col-md-4">
                                    <CategorySelector id="categoryId" className="custom-select my-1 mr-sm-2"
                                        value={this.state.categoryId} onChange={e => this.handleCategoryIdChange(e)}
                                        required msg={'project.catalog.CategorySelector.oneCategory'}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="offset-md-3 col-md-1">
                                    <button type="submit" className="btn btn-primary">
                                        <FormattedMessage id="project.catalog.Advertise.submit"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

}

export default connect()(Advertise);