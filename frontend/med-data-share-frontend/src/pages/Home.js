import React from 'react'

import { bindActionCreators } from "redux";
import connect from "react-redux/es/connect/connect";
import { withRouter } from "react-router-dom";

import Loader from "../components/Loader";
import * as Actions from "../actions/Actions";

import Page from "../common/Page";

class Home extends Page {
    constructor(props) {
        super(props);

        this.props.changeFullScreen(false);
    }

    refreshView() {
        
    }

    fetchData() {
        
    }

    render() {

        return (
            <div >                
               <Loader/>
            </div>
            
        );
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers }) {
    return { menu: menuReducers, user: authReducers.user };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));

