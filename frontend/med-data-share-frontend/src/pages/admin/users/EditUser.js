import React from 'react';

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";
import { updateUser } from '../../../services/UserService';
import { transformDate } from '../../../util/DateUtil';

import strings from "../../../localization";
import Validators from "../../../constants/ValidatorTypes";
import * as Actions from "../../../actions/Actions";

import {Paper, Grid} from "@material-ui/core";

import FormComponent from "../../../common/FormComponent";
import EditUserForm from "../../../components/forms/admin/user/EditUserForm";

class EditUser extends FormComponent {

    validationList = {
        email: [ {type: Validators.EMAIL } ],
        firstName: [ {type: Validators.REQUIRED } ],
        lastName: [ {type: Validators.REQUIRED } ],
        birthday: [ {type: Validators.DATE_FORMAT } ],
        gender: [ {type: Validators.POSSIBLE_VALUE, values: ['Male', 'Female', 'Other'] } ]

    };

    nonEditableFieldList = [
        'id', 'email', 'activeSince', 'enabled', 'role', 'medInstitution'
    ]

    constructor(props) {
        super(props);

        this.state = {
            data: props.data ? props.data : {},
            dataMeta: props.dataMeta ? props.dataMeta : {},
            errors: {}
        };

        this.props.changeFullScreen(false);

        this.submit = this.submit.bind(this);
    }

    submit() {

        if(!this.validate()) {
            return;
        }

        this.state.data['password'] = 'xxxx';
        this.state.data['activeSince'] = this.state.dataMeta['activeSince'];
        if(this.state.data['birthday']){
            this.state.data['birthday'] = transformDate(this.state.data['birthday']);
        }
        updateUser(this.state.data, this.props.user).then(response => {

            if(!response.ok) {
                this.props.onFinish();
                this.props.enqueueSnackbar(strings.profile.editError, { variant: 'error' });
                return;
            }

            this.props.enqueueSnackbar(strings.profile.editSuccess, { variant: 'success' });
            this.props.onFinish();
        });
    }

    render() {

        return (
            <Grid id='page' item md={ 12 }>

                <div className='header'>
                    <h1>{ strings.editUser.pageTitle }</h1>
                </div>

                <Paper className='paper'>
                    <EditUserForm 
                        onChange={ this.changeData } 
                        onSubmit={ this.submit }
                        onCancel={ this.props.onCancel }
                        data={ this.state.data } 
                        dataMeta={ this.state.dataMeta } 
                        nonEditableFieldList = {this.nonEditableFieldList}
                        errors={ this.state.errors }  />
                </Paper>

            </Grid>

        );
    }
}


function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers, siteDataReducers, authReducers })
{
    return { menu: menuReducers, siteData: siteDataReducers, user: authReducers.user };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(EditUser)));