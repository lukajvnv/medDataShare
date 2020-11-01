import React, {Component} from 'react';
import Loader from '../components/Loader';
import {withRouter} from 'react-router-dom';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Navigation from "../components/Navigation";
import MenuState from "../constants/MenuState";

class BaseLayout extends Component {

    getContentClass() {

        if(this.props.menu.state === MenuState.SHORT) {
            return 'content-container short';
        }
        else {
            return 'content-container';
        }
    }

    render() {

        const {children} = this.props;

        return (
            <React.Fragment>
                {
                    this.props.loader &&
                    <Loader/>
                }
                
                {
                    !this.props.menu.fullScreen &&
                    <div id='main-container'>
                        <div className='navigation-container'>
                            <Navigation/>
                        </div>
                        <div className={ this.getContentClass() }>
                            <Header/>
                            { children }
                            <Footer/>
                        </div>
                    </div>
                }

                {
                    this.props.menu.fullScreen &&
                    children
                }
                
            </React.Fragment>
        )
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({}, dispatch);
}

function mapStateToProps({ authReducers, siteDataReducers, menuReducers })
{
    return {
        loader: siteDataReducers.loader,
        menu: menuReducers,
        user: authReducers.user
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(BaseLayout));
