import React from 'react';
import Page from '../../../common/Page';

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";
import {getUserDefineAccessTrials} from '../../../services/CommonUserService';
import {dateToString} from "../../../util/DateUtil";
import {editClinicalTrial} from "../../../services/CommonUserService";

import * as Actions from "../../../actions/Actions";
import Validators from "../../../constants/ValidatorTypes";
import strings from "../../../localization";

import {
    Button, Typography, Box
} from "@material-ui/core";

import { DataGrid } from '@material-ui/data-grid';
import ClinicalTrialDetail from "../clinical_trial/ClinicalTrialDetail";
import { locallizeTrialType } from '../../../util/ClinicalTrialUtil';

import EditIcon from '@material-ui/icons/Edit';
import AccessType from '../../../constants/AccessType';

const columns = [
    { 
        field: 'id', 
        headerName: strings.clinicalTrial.detail.id, 
        width: 250,
    },
    { 
        field: 'time', 
        headerName: strings.clinicalTrial.detail.time, 
        width: 120, 
        valueGetter: (params) =>
        `${dateToString(params.getValue('time')) || ''}`, 
    },
    // { 
    //     field: 'patient', 
    //     headerName: strings.clinicalTrial.detail.patient, 
    //     width: 300 
    // },
    { 
        field: 'doctor', 
        headerName: strings.clinicalTrial.detail.doctor, 
        width: 400 
    },
    { 
        field: 'clinicalTrialType', 
        headerName: strings.clinicalTrial.detail.clinicalTrialType, 
        width: 200,
        valueGetter: (params) =>  
        `${locallizeTrialType(params.getValue('clinicalTrialType')) || ''}`
    },
    {
        field: 'detail',
        headerName: strings.clinicalTrial.detail.text,
        description: 'Detail information about clinical trial',
        sortable: false,
        width: 160,
        renderCell: ({data}) => {
            return <Button startIcon={<EditIcon />} variant="contained" color="secondary">{strings.tasklist.trialDefineAccess.action}</Button>
        }
    },
];
  
class ClinicalTrialDefineAccess extends Page {

    attributesDescription = [
        { key: 'id', label: strings.clinicalTrial.detail.id },
        { key: 'time', transform: 'formatString', label: strings.clinicalTrial.detail.time },
        // { key: 'patient', label: strings.clinicalTrial.detail.patient },
        // { key: 'doctor', label: strings.clinicalTrial.detail.doctor },
        { key: 'clinicalTrialType', transform: 'formatTrialType', label: strings.clinicalTrial.detail.clinicalTrialType },
        { key: 'introduction', label: strings.clinicalTrial.detail.introduction },
        { key: 'relevantParameters', label: strings.clinicalTrial.detail.relevantParameters },   
        { key: 'conclusion', label: strings.clinicalTrial.detail.conclusion },     
    ];

    validationList = {
        accessType: [ {type: Validators.REQUIRED } ],
    };

    constructor(props) {
        super(props);

        this.state = {
            displayDetailView: false,
            selectedClinicalTrial: undefined,
            clinicalTrials: [],
            data: props.data ? props.data : {},
            errors: {},
            displayProgress: false
        }

        this.props.changeFullScreen(false);

        this.onRowClick = this.onRowClick.bind(this);
        this.onRowSelected = this.onRowSelected.bind(this);
        this.closeDetailView = this.closeDetailView.bind(this);
        this.editClinicalTrial = this.editClinicalTrial.bind(this);
    }

    onRowClick(param){
        this.setState({
            displayDetailView: true,
            selectedClinicalTrial: param.data
        });

        console.log(param);
    }

    onRowSelected(param){
        console.log(param);
    }

    fetchData(){
        getUserDefineAccessTrials(AccessType.IDLE)
        .then(response => {
            if(!response.ok){
                return;
            }
            console.log(response);
            this.setState({clinicalTrials: response.data})
        })
        .catch(error => {
            console.log(error);
        });
    }

    closeDetailView(){
        this.setState({displayDetailView: false});
    }

    editClinicalTrial(){
        if(!this.validate()) {
            return;
        }
        
        const data = this.state.data;
        data['id'] = this.state.selectedClinicalTrial.id;

        this.setState({displayProgress: true});

        editClinicalTrial(data)
        .then(response => {
            if(!response.ok){
                this.setState({displayProgress: false});
                return;
            }
            console.log(response);
            this.props.enqueueSnackbar(strings.tasklist.trialDefineAccess.defineAccessSuccess, { variant: 'success' });
            this.setState({displayDetailView: false, displayProgress: false});
            this.fetchData();
        })
        .catch(error => {
            console.log(error);
            this.setState({displayProgress: false});
        });
    }

    render() {
        return (
            <div >
                <Typography 
                    component="h1"
                    variant="h6"
                    align="center"
                    color="primary"
                >
                    {strings.tasklist.trialDefineAccess.pageTitle}
                </Typography>
                <Box m={2}>
                    <DataGrid 
                        rows={this.state.clinicalTrials} 
                        columns={columns} 
                        pageSize={5} 
                        page={1}
                        autoHeight 
                        onRowClick={this.onRowClick}
                        onRowSelected={this.onRowSelected}
                    />
                </Box>
                
                {
                    this.state.displayDetailView && 
                        <ClinicalTrialDetail
                            shouldOpen={this.state.displayDetailView}
                            clinicalTrial={this.state.selectedClinicalTrial}
                            onClose={ this.closeDetailView }
                            onSubmit={ this.editClinicalTrial }
                            onChange={ this.changeData } 
                            attributesDescription={this.attributesDescription}
                            form={true}
                            data={this.state.data}
                            errors={this.state.errors}
                            submitLabel={strings.tasklist.trialDefineAccess.submit}
                            displayResource={true}
                            displayPdfExportButton={true}
                            displayProgress={this.state.displayProgress}
                        />
                }
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen,
        loadUser: Actions.loadUser
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers }) {
    return { menu: menuReducers };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(ClinicalTrialDefineAccess)));
