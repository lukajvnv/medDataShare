import React from 'react';
import Page from "../../../common/Page";

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {resetPassword} from "../../../services/UserService";

import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";

import {Grid, Paper} from '@material-ui/core';
import ResetPasswordForm from "../../../components/forms/user/ResetPasswordForm";
import Validators from "../../../constants/ValidatorTypes";

class ResetPassword extends Page {

    validationList = {
        password: [ {type: Validators.REQUIRED } ],
        passwordRepeat: [ {type: Validators.REQUIRED } ],
    };

    params = [{ name: 'token', default: '' }];

    constructor(props) {
        super(props);

        this.state = {
            data: {},
            errors: {},
        };

        this.keyPress = this.keyPress.bind(this);
    }

    componentDidMount() {
        if(!this.props.auth.user) {
            this.props.history.push('/');
        }
    }

    keyPress(event) {

        if(event.key === 'Enter') {
            this.resetPassword()
        }
    }

    passwordMatch() {

        return this.state.data.password === this.state.data.passwordRepeat;
    }

    resetPassword() {

        let data = this.state.data;

        if(!this.validate()) {
            return;
        }

        if(!this.passwordMatch()) {

            this.setError('password', strings.editUser.passwordDoNotMatch);
            return;
        }

        resetPassword(data).then(response => {

            if(!response.ok) {
                const error = response;
                this.setError('password', error.response.data.message);
                return;
            }

            this.props.onFinish();
        });
    }

    render() {

        return (

            <div id='login'>
                <Grid item md={6}>
                    <Paper className='paper'>

                        <h1>{ strings.resetPassword.resetPassword }</h1>

                        <ResetPasswordForm 
                            onSubmit={ () => this.resetPassword() } 
                            onCancel={ this.props.onCancel }
                            onChange={ this.changeData }                      
                            keyPress={ this.keyPress }
                            data={ this.state.data } 
                            errors={ this.state.errors }/>
                    </Paper>
                </Grid>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers })
{
    return { menu: menuReducers, auth: authReducers };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(ResetPassword));