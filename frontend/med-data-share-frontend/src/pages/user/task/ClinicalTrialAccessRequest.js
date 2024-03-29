import React from 'react';
import Page from '../../../common/Page';
import ClinicalTrialDetail from '../clinical_trial/ClinicalTrialDetail';

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";
import Validators from "../../../constants/ValidatorTypes";
import {getClinicalTrial, trialAccessRequestDecision} from "../../../services/UserService";
import {dateToString} from "../../../util/DateUtil";
import moment from "moment";

import * as Actions from "../../../actions/Actions";
import strings from "../../../localization";

import {
    Card, CardActionArea, CardActions, CardContent, 
    Box, Grid, Paper,
    Typography, Button
} from "@material-ui/core";

import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import AddClinicalTrialAccesDecision from '../clinical_trial/AddClinicalTrialAccessDecision';

class ClinicalTrialAccessRequest extends Page {

    clinicalTrialDescription = [
        { key: 'id', label: strings.clinicalTrial.detail.id },
        { key: 'time', transform: 'formatString', label: strings.clinicalTrial.detail.time },
        // { key: 'patient', label: strings.clinicalTrial.detail.patient },
        { key: 'doctor', label: strings.clinicalTrial.detail.doctor },
        { key: 'institution', label: strings.clinicalTrial.detail.institution },
        { key: 'clinicalTrialType', transform: 'formatTrialType', label: strings.clinicalTrial.detail.clinicalTrialType },
        { key: 'introduction', label: strings.clinicalTrial.detail.introduction},
        { key: 'relevantParameters', label: strings.clinicalTrial.detail.relevantParameters },   
        { key: 'conclusion', label: strings.clinicalTrial.detail.conclusion },     
    ];

    trialAccessRequestDescription = [
        { key: 'id', transform: 'renderId', label: strings.tasklist.trialAccessRequest.view.id },
        { key: 'time', label: strings.tasklist.trialAccessRequest.view.time, transform: 'renderColumnDate' },
        { key: 'sender', transform: 'renderUser', label: strings.tasklist.trialAccessRequest.view.sender },
        // { key: 'receiver', label: strings.tasklist.trialAccessRequest.view.receiver },
        { key: 'clinicalTrialType', transform: 'renderTrialType', label: strings.tasklist.trialAccessRequest.view.clinicalTrialType },
        // { key: 'clinicalTrial', label: strings.tasklist.trialAccessRequest.view.clinicalTrial },
        { key: 'from', label: strings.tasklist.trialAccessRequest.view.from, transform: 'renderColumnDate' },
        { key: 'until', label: strings.tasklist.trialAccessRequest.view.until, transform: 'renderColumnDate' },   
        { key: 'accessDecision', transform: 'renderTrialAccessRights', label: strings.tasklist.trialAccessRequest.view.accessDecision },     
        { key: 'anonymity', label: strings.tasklist.trialAccessRequest.view.anonymity, transform: 'renderColumnDeleted' },     
    ];

    validationList = {
        accessDecision: [ {type: Validators.REQUIRED } ],
        // from: [ {type: Validators.REQUIRED },  {type: Validators.VALID_DATE } ],
        // until: [ {type: Validators.REQUIRED },  {type: Validators.VALID_DATE }],
    };

    constructor(props) {
        super(props);

        // const clinicalTrial = {
        //     id: "5f99d86e078a070d9c94e34c",
        //     time: "2020-10-15T10:00:00.000+00:00",
        //     introduction: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        //     relevantParameters: null,
        //     conclusion: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        //     clinicalTrialType: "CBC",
        //     patient: "402881847570f569017570f5701d0002",
        //     doctor: "402881847570f569017570f570cc0004",
        //     accessType: "IDLE"
        // };

        const clinicalTrialRequest = props.location.state.clinicalTrialRequest;
        const requiredDecision = props.location.state.requiredDecision;
        const shouldDisplayTrial = props.location.state.shouldDisplayTrial;
        const currentTab = props.location.state.currentTab;
        const requestType = props.location.state.requestType;

        const data = props.data ? props.data : {};
        const halfYearFuture = moment().add(3, 'month')
        data['from'] = dateToString(new Date(), "YYYY-MM-DD");
        data['until'] = dateToString(halfYearFuture, "YYYY-MM-DD");

        this.state = {
            displayDetailView: false,
            displayAccessRequestDecision: false,
            selectedClinicalTrial: undefined,
            clinicalTrials: [],
            data: data,
            clinicalTrialRequest: clinicalTrialRequest ? clinicalTrialRequest : {},
            errors: {},
            requiredDecision: requiredDecision,
            shouldDisplayTrial: shouldDisplayTrial,
            currentTab: currentTab,
            displayProgress: false,
            requestType: requestType
        }

        this.closeDetailView = this.closeDetailView.bind(this);
        this.openDetailView = this.openDetailView.bind(this);
        this.closeClinicalAccessDecision = this.closeClinicalAccessDecision.bind(this);
        this.addClinicalAccessDecision = this.addClinicalAccessDecision.bind(this);
        this.goBack = this.goBack.bind(this);

    }

    addClinicalAccessDecision(){
        if(!this.validate()) {
            return;
        }
        
        const data = this.state.data;
        data['id'] = this.state.clinicalTrialRequest.id;

        // const from = stringToDate(data.from, "YYYY-MM-DD");
        // const until = stringToDate(data.until, "YYYY-MM-DD");

        // if(!dateBefore(from, until)){
        //     const errors = {};
        //     const fieldErrors = [];
        //     fieldErrors.push(
        //     {
        //         message: 'Until should be after from'
        //     });
        //     errors['from'] = fieldErrors;
        //     errors['until'] = fieldErrors;
        //     this.setState({errors});
        //     return;
        // }

        this.setState({displayProgress: true});

        trialAccessRequestDecision(data)
        .then(response => {
            if(!response.ok){
                this.setState({displayProgress: false});
                return;
            }
            console.log(response);
            this.props.enqueueSnackbar(strings.tasklist.trialAccessRequest.form.decisionSuccess, { variant: 'success' });
            this.setState({displayProgress: false});
            this.goBack();
        })
        .catch(error => {
            console.log(error);
            this.setState({displayProgress: false});
        });
    }

    closeClinicalAccessDecision(){
        this.setState({displayAccessRequestDecision: false});
    }

    closeDetailView(){
        this.setState({displayDetailView: false});
    }

    openDetailView(){
        if(this.state.shouldDisplayTrial){
            let accessUserRole = '';
            if(this.state.requestType === 'requested'){
               accessUserRole = 'requester'; 
            } else {
                accessUserRole = 'patient';
            }

            const resId = this.state.clinicalTrialRequest.id;
            getClinicalTrial(resId, accessUserRole) 
            .then(response => {
                if(!response.ok){
                    return;
                }
                console.log(response);
                this.setState({displayDetailView: true, selectedClinicalTrial: response.data});
            })
            .catch(error => {
                console.log(error);
            });
        }
    }

    goBack(){
        // this.props.history.goBack();
        this.props.history.push({
            pathname: '/tasks',
            state: {currentActiveTab: this.state.currentTab}
        })
    }

    renderTrialAccessRequestAttribute(trialAccessRequestAttributesMeta, trialAccessRequest){
        const key = trialAccessRequestAttributesMeta.key;
        const label = trialAccessRequestAttributesMeta.label;

        let value = trialAccessRequest[key];
    
        const transformation = trialAccessRequestAttributesMeta.transform;
        if(transformation){
            value = this[transformation](value);
        } 
       
        return <Grid container spacing={2} key={key}>
                    <Grid item xs={6} md={6}>
                        <Paper className='paper'>
                            <Typography variant="body1" color="textSecondary" component="h5">
                                {label}
                            </Typography>
                        </Paper>
                    </Grid>
                    <Grid item xs={6} md={6}>
                        <Paper className='paper'>
                            <Typography variant="body1" color="textPrimary" component="h5">
                                {value}
                            </Typography>
                        </Paper>
                    </Grid>
            </Grid>
    }

    render() {
        const clinicalTrialRequest = this.state.clinicalTrialRequest;
        return (
            <div >
                <Card className="mediaContainer">
                    <CardActionArea>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                {strings.tasklist.trialAccessRequest.pageTitleSingular}
                            </Typography>
                            <Typography gutterBottom variant="subtitle2" component="h2">
                                {strings.tasklist.trialAccessRequest.dateCreated} {this.renderColumnDate(clinicalTrialRequest.time)}
                            </Typography>
                            <Typography variant="body1" color="textSecondary" component="h5">
                                {strings.tasklist.trialAccessRequest.clinicalTrialType} {this.renderTrialType(clinicalTrialRequest.clinicalTrialType)}
                            </Typography>
                        </CardContent>
                    </CardActionArea>
                    <CardActions>
                        <Button
                            color="primary"
                            startIcon={<ArrowBackIcon />}
                            variant="outlined"
                            onClick={this.goBack}
                        >
                            {strings.tasklist.trialAccessRequest.back}
                        </Button>
                        {
                            this.state.requiredDecision && 
                            <Button
                                color="secondary"
                                startIcon={<EditIcon />}
                                variant="contained"
                                onClick={() => this.setState({displayAccessRequestDecision: true})}
                            >
                                {strings.tasklist.trialAccessRequest.decide}
                            </Button>
                        }
                        <Button
                            color="primary"
                            startIcon={<GetAppIcon />}
                            variant="contained"
                            onClick={this.openDetailView}
                        >
                            {strings.tasklist.trialAccessRequest.get}
                        </Button>
                    </CardActions>
                </Card>
                <Box mt={2}>
                    <Card className="mediaContainer">
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                {strings.tasklist.trialAccessRequest.requestInformation}
                            </Typography>
                            {
                               this.trialAccessRequestDescription.map(trialAccessRequest => 
                                    this.renderTrialAccessRequestAttribute(trialAccessRequest, clinicalTrialRequest))
                            }
                        </CardContent>
                    </Card>
                </Box>
                {
                    this.state.displayDetailView && 
                        <ClinicalTrialDetail
                            shouldOpen={this.state.displayDetailView}
                            clinicalTrial={this.state.selectedClinicalTrial}
                            onClose={ this.closeDetailView }
                            onSubmit={ this.closeDetailView }
                            attributesDescription={this.clinicalTrialDescription}
                            form={false}
                            submitLabel={strings.clinicalTrial.detail.ok}
                            displayResource={true}
                            displayPdfExportButton={true}
                            />
                }
                {
                    this.state.displayAccessRequestDecision && 
                        <AddClinicalTrialAccesDecision
                            shouldOpen={this.state.displayAccessRequestDecision}
                            onClose={ this.closeClinicalAccessDecision }
                            onSubmit={ this.addClinicalAccessDecision }
                            onChange= { this.changeData }
                            onSwitchChange={ this.onSwitchChange }
                            data={this.state.data}
                            errors={this.state.errors}
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

function mapStateToProps({ menuReducers }) {
    return { menu: menuReducers };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(ClinicalTrialAccessRequest)));
