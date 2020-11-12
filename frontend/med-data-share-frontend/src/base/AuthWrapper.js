import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import { checkPath, hasProprieteRole } from '../route';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {isUserLocked, isUserLoggedIn} from "./Auth";
import { getUserFromLocalStorage } from './HTTP';
import strings from '../localization';

class AuthWrapper extends Component {

    componentDidUpdate(prevProps) {
        if (this.props.location.pathname !== prevProps.location.pathname) {
            window.scrollTo(0, 0);
        }
    }

    checkPermission() {

        const path = this.props.location.pathname;
        const isUserLogged = isUserLoggedIn();
        
        if(path === '/signIn' && isUserLogged){
            this.props.history.goBack();
            return false;
        }
        
        let needAuth = checkPath(path);

        if(needAuth && isUserLocked()) {

            this.props.history.push({
                pathname: '/lock',
                state   : { redirectUrl: path }
            });

            return false;
        } else if(needAuth && !isUserLogged) {

            this.props.history.push({
                pathname: '/signIn',
                state   : { redirectUrl: path }
            });

            return false;
        } else if(needAuth && !hasProprieteRole(path, getUserFromLocalStorage())){
            
            this.props.history.push({
                pathname: '/forbidden',
                state   : { msg: `${strings.forbidden.unAuthorizedAccess}'${path}'`}
            });
            
            return false;
        }

        return true;
    }

    render() {

        if(!this.checkPermission()) {
            return '';
        }

        const {children} = this.props;

        return (
            <React.Fragment>
                { children }
            </React.Fragment>
        )
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({}, dispatch);
}

function mapStateToProps({ authReducers })
{
    return {
        auth: authReducers,
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AuthWrapper));
