import React from 'react';
import FormComponent from "../../../common/FormComponent";

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import { addDoctor } from '../../../services/MedAdminService';
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";

import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";
import Validators from "../../../constants/ValidatorTypes";

import {Paper, Grid} from "@material-ui/core";

import DoctorForm from '../../../components/forms/admin/med_institution/DoctorForm';

class AddDoctor extends FormComponent {

    validationList = {
        email: [ {type: Validators.EMAIL } ],
        password: [ {type: Validators.REQUIRED } ],
        firstName: [ {type: Validators.REQUIRED } ],
        lastName: [ {type: Validators.REQUIRED } ],
        occupation: [ {type: Validators.REQUIRED } ],
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

    submit() {

        if(!this.validate()) {
            return;
        }

        this.showDrawerLoader();
        
        addDoctor(this.state.data).then(response => {

            if(!response.ok) {
                this.props.onFinish(null);
                const error = response.response;
                const detailErrorMessage = error ? ': ' + error.data.message : ''
                this.props.enqueueSnackbar(strings.addDoctor.errorAddingDoctor + detailErrorMessage, { variant: 'error' });
                return;
            }

            this.props.enqueueSnackbar(strings.addDoctor.doctorAdd, { variant: 'success' });
            this.props.onFinish(response.data);

            this.hideDrawerLoader();
        });
    }

    render() {
        return (
            <Grid id='page' item md={ 12 }>

                <div className='header'>
                    <h1>{ strings.doctorList.pageTitle } [{this.props.medInstitutionName}]</h1>
                </div>

                <Paper className='paper'>
                    <DoctorForm 
                        onChange={ this.changeData } 
                        onSubmit={ this.submit }
                        data={ this.state.data } 
                        errors={ this.state.errors } 
                        onCancel={ this.props.onCancel }
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(AddDoctor)));