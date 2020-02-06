import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import Products from './Products';
import queryString from 'query-string'

const initialState = {
    categoryId: '',
    keywords: '',
    page: ''
};

class FindProductsResult extends React.Component {

    constructor(props) {
        super(props);
        this.state = initialState;
    }

    toNumber(value) {
        return value.length > 0 ? Number(value) : null;
    }

    componentDidMount() {
        const productSearch = this.props.productSearch;
        const values = queryString.parse(this.props.history.location.search);
        this.setState({
            categoryId: this.toNumber(values.cat==null? 0 : values.cat),
            keywords: values.search==null? '' : values.search,
            page: values.p==null? 0 : values.p
        });
        
        if (!productSearch || !productSearch.criteria){
            this.props.findProducts(this.state);
        }else if(this.state.categoryId !== this.props.productSearch.criteria.categoryId 
                || this.state.keywords !== this.props.productSearch.criteria.keywords 
                || this.state.page !== this.props.productSearch.criteria.page+''){
                this.props.findProducts(this.state);
        }
    }
    
    render(){
        const productSearch = this.props.productSearch;
         
        if (!productSearch) {
            return null;
        }

        if (productSearch.result.items.length === 0) {
            return (
                <div className="alert alert-danger" role="alert">
                    <FormattedMessage id='project.catalog.FindProductsResult.noProductsFound'/>
                </div>
            );
        }

        return(
        <div>
            <Products products={this.props.productSearch.result.items} categories={this.props.categories}/>
            <Pager 
                back={{
                    enabled: productSearch.criteria.page >= 1,
                    onClick: () => this.props.previousFindProductsResultPage(productSearch.criteria)}}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    onClick: () => this.props.nextFindProductsResultPage(productSearch.criteria)}}/>
        </div>)
    }
}

const mapStateToProps = (state) => ({
    productSearch: selectors.getProductSearch(state),
    categories: selectors.getCategories(state)
});

const mapDispatchToProps = {
    previousFindProductsResultPage: actions.previousFindProductsResultPage,
    nextFindProductsResultPage: actions.nextFindProductsResultPage,
    findProducts: actions.findProducts
}

export default connect(mapStateToProps, mapDispatchToProps)(FindProductsResult);