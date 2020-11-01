import React from 'react';

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {login} from "../../../base/Auth";

import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";

import Page from "../../../common/Page";

import {Grid, Paper} from '@material-ui/core';
import SignInForm from "../../../components/forms/user/SignInForm";
import Validators from "../../../constants/ValidatorTypes";

class SignIn extends Page {

    validationList = {
        email: [ {type: Validators.EMAIL } ],
        password: [ {type: Validators.REQUIRED } ]
    };

    constructor(props) {
        super(props);

        this.state = {
            data: {},
            errors: {},
            redirectUrl: props.location.state ? props.location.state.redirectUrl : '/'
        };

        this.props.changeFullScreen(true);

        this.keyPress = this.keyPress.bind(this);
    }

    componentDidMount() {

        if(this.props.auth.user) {
            this.props.history.push('/');
        }
    }

    keyPress(event) {

        if(event.key === 'Enter') {
            this.login()
        }
    }

    login() {

        if(!this.validate()) {
            return;
        }

        login(this.state.data.email, this.state.data.password).then(response => {

            if(!response.ok) {

                this.setError('email', strings.login.wrongCredentials);
                return;
            }

            this.props.loadUser();
            
            this.props.history.push({
                pathname: this.state.redirectUrl
            })
        }).catch(error => {});
    }

    render() {

        return (

            <div id='login'>
                <Grid item md={6}>
                    <Paper className='paper'>

                        <h1>{ strings.login.login }</h1>

                        <SignInForm onSubmit={ () => this.login() } onChange={ this.changeData }
                                   keyPress={ this.keyPress }
                                   data={ this.state.data } errors={ this.state.errors }/>
                    </Paper>
                </Grid>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen,
        login: Actions.login,
        loadUser: Actions.loadUser
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers })
{
    return { menu: menuReducers, auth: authReducers };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(SignIn));