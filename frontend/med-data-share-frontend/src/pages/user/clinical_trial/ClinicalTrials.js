import React from 'react';
import Page from '../../../common/Page';

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";
import {getUserClinicalTrials} from '../../../services/CommonUserService';
import {dateToString} from "../../../util/DateUtil";

import * as Actions from "../../../actions/Actions";
import strings from "../../../localization";

import {
    Button, Typography, Box
} from "@material-ui/core";

import { DataGrid } from '@material-ui/data-grid';
import ClinicalTrialDetail from "./ClinicalTrialDetail";

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
    { 
        field: 'patient', 
        headerName: strings.clinicalTrial.detail.patient, 
        width: 300 
    },
    { 
        field: 'doctor', 
        headerName: strings.clinicalTrial.detail.doctor, 
        width: 300 
    },
    { 
        field: 'clinicalTrialType', 
        headerName: strings.clinicalTrial.detail.clinicalTrialType, 
        width: 100 
    },
    { 
        field: 'accessType', 
        headerName: strings.clinicalTrial.detail.accessType, 
        width: 200 
    },
    {
        field: 'detail',
        headerName: 'Detail',
        description: 'Detail information about clinical trial',
        sortable: false,
        renderCell: ({data}) => {
            return <Button variant="contained" color="primary">{strings.clinicalTrial.detail.text}</Button>
        }
      },
];
  
class ClinicalTrials extends Page {

    attributesDescription = [
        { key: 'id', label: strings.clinicalTrial.detail.id },
        { key: 'time', transform: 'formatString', label: strings.clinicalTrial.detail.time },
        { key: 'patient', label: strings.clinicalTrial.detail.patient },
        { key: 'doctor', label: strings.clinicalTrial.detail.doctor },
        { key: 'clinicalTrialType', label: strings.clinicalTrial.detail.clinicalTrialType },
        { key: 'introduction', label: strings.clinicalTrial.detail.introduction },
        { key: 'relevantParameters', label: strings.clinicalTrial.detail.relevantParameters },   
        { key: 'conclusion', label: strings.clinicalTrial.detail.conclusion },
        { key: 'accessType', label: strings.clinicalTrial.detail.accessType } 
     
    ];

    constructor(props) {
        super(props);

        this.state = {
            displayDetailView: false,
            selectedClinicalTrial: undefined,
            clinicalTrials: []
        }

        this.props.changeFullScreen(false);

        this.onRowClick = this.onRowClick.bind(this);
        this.onRowSelected = this.onRowSelected.bind(this);
        this.closeDetailView = this.closeDetailView.bind(this);
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
        getUserClinicalTrials()
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

    render() {
        return (
            <div >
                <Box m={2}>
                    <Typography 
                        component="h1"
                        variant="h2"
                        align="center"
                        color="primary"
                    >
                        {strings.clinicalTrial.pageTitle}
                    </Typography>
                </Box>
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
                            onClose={this.closeDetailView}
                            onSubmit={this.closeDetailView}
                            attributesDescription={this.attributesDescription}
                            form={false}
                            submitLabel={strings.clinicalTrial.detail.ok}
                            displayResource={true}
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(ClinicalTrials)));
