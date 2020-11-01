import React from 'react';
import Page from '../../../common/Page';

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {addMedInstitutionAdmin, getMedInstitutions} from "../../../services/SuperAdminService";
import {withSnackbar} from "notistack";

import strings from "../../../localization";
import Validators from "../../../constants/ValidatorTypes";
import * as Actions from "../../../actions/Actions";

import {Paper, Grid} from "@material-ui/core";

import MedAdminForm from "../../../components/forms/admin/med_institution/MedAdminForm";

class AddMedAdmin extends Page {

    validationList = {
        email: [ {type: Validators.EMAIL } ],
        password: [ {type: Validators.REQUIRED } ],
        firstName: [ {type: Validators.REQUIRED } ],
        lastName: [ {type: Validators.REQUIRED } ],
        medInstitution: [ {type: Validators.REQUIRED } ]
    };

    constructor(props) {
        super(props);

        this.state = {
            data: props.data ? props.data : {},
            medInstitutionsDataSource: [],
            errors: {}
        };

        this.props.changeFullScreen(false);

        this.submit = this.submit.bind(this);
    }

    fetchData(){
        getMedInstitutions()
        .then(response => {
            this.setState({
                medInstitutionsDataSource: response.data,
            });
        }).catch(error => {

        });
    }

    submit() {

        if(!this.validate()) {
            return;
        }

        this.showDrawerLoader();

        addMedInstitutionAdmin(this.state.data).then(response => {

            if(!response.ok) {
                this.props.onFinish(null);
                const error = response.response;
                const detailErrorMessage = error ? ': ' + error.data.message : ''
                this.props.enqueueSnackbar(strings.addMedAdmin.errorAddingMedAdmin + detailErrorMessage, { variant: 'error' });
                return;
            }

            this.props.enqueueSnackbar(strings.addMedAdmin.medAdminAdd, { variant: 'success' });
            this.props.onFinish(response.data);

            this.hideDrawerLoader();
        });
    }

    render() {
        return (
            <Grid id='page' item md={ 12 }>

                <div className='header'>
                    <h1>{ strings.addMedAdmin.pageTitle }</h1>
                </div>

                <Paper className='paper'>
                    <MedAdminForm 
                        onChange={ this.changeData } 
                        onSubmit={ this.submit }
                        data={ this.state.data } 
                        errors={ this.state.errors } 
                        onCancel={ this.props.onCancel }
                        medInstitutionsDataSource={this.state.medInstitutionsDataSource}
                    />
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

function mapStateToProps({ menuReducers, siteDataReducers })
{
    return { menu: menuReducers, siteData: siteDataReducers };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(AddMedAdmin)));