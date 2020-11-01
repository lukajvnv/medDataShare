import React from 'react';
import Page from '../../common/Page';

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";

import strings from "../../localization";
import Validators from "../../constants/ValidatorTypes";
import * as Actions from "../../actions/Actions";
import FileFormat from "../../constants/FileFormat";

import {Paper, Grid} from "@material-ui/core";

import { addClinicalTrial } from '../../services/DoctorService';
import AddClinicalTrialForm from '../../components/forms/doctor/AddClinicalTrialForm';
import FindPatientAutocomplete from './FindPatientAutocomplete';
import {getPatients} from '../../services/DoctorService';

class AddClinicalTrial extends Page {

    validationList = {
        clinicalTrialType: [ {type: Validators.REQUIRED } ],
        introduction: [ {type: Validators.REQUIRED } ],
        conclusion: [ {type: Validators.REQUIRED } ],
        file: [ {type: Validators.REQUIRED } ],
        patient: [ {type: Validators.REQUIRED } ]
    };

    constructor(props) {
        super(props);

        this.state = {
            data: props.data ? props.data : {},
            dataMeta: props.dataMeta ? props.dataMeta : {},
            errors: {},
            patients: []
        };

        this.props.changeFullScreen(false);

        this.submit = this.submit.bind(this);
        this.keyPress = this.keyPress.bind(this);
        this.onFileUpload = this.onFileUpload.bind(this);
        this.setPatient = this.setPatient.bind(this);
    }

    fetchData(){
        getPatients({
            
        }).then(response => {

            if(!response.ok) {
                return;
            }

            const patients = response.data;
            
            this.setState({patients});
        });
    }

    setPatient(patient){
        const data = this.state.data;
        data['patient'] = patient;
        this.setState({data});
    }

    onFileUpload(event){
        console.log(event);

        let fileList = event.target.files;
        let file = fileList.item(0);
    
        let formData = new FormData(); 
        formData.append('file', file, file.name); 
        const fileType = file.type;
        if(fileType !== FileFormat.jpg && fileType !== FileFormat.png){
            this.props.enqueueSnackbar(strings.clinicalTrial.form.fileUploadError, {variant: 'error'});
            return;
        }

        const data = this.state.data;
        data['file'] = file;
        this.setState({data});
    }

    keyPress(event) {

        if(event.key === 'Enter') {
            this.submit();
        }
    }

    submit() {

        if(!this.validate()) {
            return;
        }

        let formData = new FormData(); 

        for(const [key, value] of Object.entries(this.state.data)){
            formData.append(key, value);
        }

        addClinicalTrial(formData).then(response => {

            if(!response.ok) {
                this.props.enqueueSnackbar(strings.clinicalTrial.form.error, { variant: 'error' });
                return;
            }

            this.props.enqueueSnackbar(strings.clinicalTrial.form.success, { variant: 'success' });
            this.props.history.push("/");
        });
    }

    render() {

        return (
            <div id='clinicalTrial'>
                <Grid item md={ 6 }>
                    <div className='header'>
                        <h1>{ strings.clinicalTrial.form.pageTitle }</h1>
                    </div>

                    <Paper className='paper'>
                        <FindPatientAutocomplete 
                            setPatient={this.setPatient}
                            users={this.state.patients}
                        />
                        <AddClinicalTrialForm 
                            onChange={ this.changeData } 
                            onSubmit={ this.submit }
                            onFileUpload={ this.onFileUpload }
                            onCancel={ this.props.onCancel }
                            data={ this.state.data } 
                            errors={ this.state.errors }  
                        /> 
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

function mapStateToProps({ menuReducers, siteDataReducers, authReducers })
{
    return { menu: menuReducers, siteData: siteDataReducers, user: authReducers.user };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(AddClinicalTrial)));