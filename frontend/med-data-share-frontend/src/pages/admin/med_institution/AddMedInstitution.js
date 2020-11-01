import React from 'react';
import FormComponent from "../../../common/FormComponent";

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {addMedInstitution} from "../../../services/SuperAdminService";
import {withSnackbar} from "notistack";

import strings from "../../../localization";
import Validators from "../../../constants/ValidatorTypes";
import * as Actions from "../../../actions/Actions";

import {Paper, Grid} from "@material-ui/core";

import MedInstitutionForm from "../../../components/forms/admin/med_institution/MedInstitutionForm";

class AddMedInstitution extends FormComponent {

    validationList = {
        name: [ {type: Validators.REQUIRED } ],
        address: [ {type: Validators.REQUIRED } ]
    };

    constructor(props) {
        super(props);

        this.state = {
            data: props.data ? props.data : {},
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

        addMedInstitution(this.state.data).then(response => {

            if(!response.ok) {
                this.props.onFinish(null);
                this.props.enqueueSnackbar(strings.addMedInstitution.errorAddingMedInstitution, { variant: 'error' });
                return;
            }

            this.props.enqueueSnackbar(strings.addMedInstitution.medInstitutionAdded, { variant: 'success' });
            this.props.onFinish(response.data);

            this.hideDrawerLoader();
        });
    }

    render() {

        return (
            <Grid id='page' item md={ 12 }>

                <div className='header'>
                    <h1>{ strings.addMedInstitution.pageTitle }</h1>
                </div>

                <Paper className='paper'>
                    <MedInstitutionForm onChange={ this.changeData } onSubmit={ this.submit }
                                data={ this.state.data } errors={ this.state.errors } onCancel={ this.props.onCancel }/>
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(AddMedInstitution)));