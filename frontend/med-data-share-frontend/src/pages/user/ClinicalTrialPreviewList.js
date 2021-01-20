import React from 'react'
import Page from "../../common/Page";

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {stringToDate, dateBefore} from "../../util/DateUtil";
import {getMedInstitutions, getClinicalTrialPreview, sendAccessRequest} from "../../services/UserService";
import {withSnackbar} from "notistack";

import * as Actions from "../../actions/Actions";
import strings from '../../localization';
import CONFIG from "../../config";

import {
    Button, Card, CardActions,
    Typography, Paper,
    CardActionArea, CardContent, Grid
} from '@material-ui/core';

import Pagination from '@material-ui/lab/Pagination';
import SearchTrialForm from "../../components/forms/user/SearchTrialForm";

import ClinicalTrialDetail from "../user/clinical_trial/ClinicalTrialDetail";

class ClinicalTrialPreviewList extends Page {
    clinicalTrialPreviewDescription = [
        { key: 'clinicalTrial', label: strings.clinicalTrial.detail.clinicalTrial },
        { key: 'time', transform: 'formatString', label: strings.clinicalTrial.detail.time },
        { key: 'clinicalTrialType', label: strings.clinicalTrial.detail.clinicalTrialType },
        { key: 'accessType', label: strings.clinicalTrial.detail.accessType },
        { key: 'institution', label: strings.clinicalTrial.detail.institution },
        { key: 'patientId', label: strings.clinicalTrial.detail.patient },
    ];

    validationList = {
        
    };

    constructor(props) {
        super(props);

        this.props.changeFullScreen(false);

        this.state = this.getInitialState();

        this.paginSelect = this.paginSelect.bind(this);
        this.onSearchSubmit = this.onSearchSubmit.bind(this);
        this.onSearchCancel = this.onSearchCancel.bind(this);
        this.openDetailView = this.openDetailView.bind(this);
        this.closeDetailView = this.closeDetailView.bind(this);
        this.sendRequest = this.sendRequest.bind(this);
    }

    paginSelect(event, page) {
        console.log(event);
        this.setState({page});
        this.fetchData(page);
    }

    getInitialState = () => ({
        data: {
            
        },
        errors: {},
        clinicalTrialPreviewList: [],
        institutions: [],
        pages: CONFIG.pages,
        page: 1,
        displayDetailView: false,
        selectedClinicalTrial: undefined,
    })

    componentDidMount() {
        this.fetchData();
    }

    componentWillUnmount() {

    }

    componentDidUpdate() {
        console.log('did update');
    }

    fetchData(page = undefined) {
        
        getMedInstitutions()
        .then(response => {
            const institutions = response.data.map(institution => ({
                key: institution.id, label: institution.name + ', ' + institution.address
            }));
            this.setState({institutions});
        }).catch(error => {

        });
        
        this.getClinicalTrialsPreview(page);
    }

    getClinicalTrialsPreview(page){
        const data = this.state.data;

        const currentPage = page ? page : this.state.page;
        const perPage = this.state.pages;

        const params = {
            page: currentPage, 
            perPage: perPage
        }
        
        getClinicalTrialPreview(data, params)
        .then(response => {
            console.log(response);
            this.setState({clinicalTrialPreviewList: response.data})
            //broj paginacija....
        }).catch(error => {

        });
    }

    datesAreValid(){
        const data = this.state.data;

        if((data.from && !data.until) || (!data.from && data.until)){
            const errors = {};
            const fieldErrors = [];
            fieldErrors.push(
            {
                message: 'Until should be after from'
            });
            errors['from'] = fieldErrors;
            errors['until'] = fieldErrors;
            this.setState({errors});
            return false;            
        } else if (data.from && data.until ) {
            const from = stringToDate(data.from, "YYYY-MM-DD");
            const until = stringToDate(data.until, "YYYY-MM-DD");
            if(!dateBefore(from, until)){
                const errors = {};
                const fieldErrors = [];
                fieldErrors.push(
                {
                    message: 'Until should be after from'
                });
                errors['from'] = fieldErrors;
                errors['until'] = fieldErrors;
                this.setState({errors});
                return false;
            }
        }
        return true;
    }

    onSearchSubmit(){
        if(!this.validate()) {
            return;
        }

        if(!this.datesAreValid()){
            return;
        }

        this.getClinicalTrialsPreview();
    }

    onSearchCancel(){
        this.setState({data: {}});
    }

    sendRequest(){
        if(!this.validate()) {
            return;
        }
        
        const data = this.state.data;
        data['clinicalTrial'] = this.state.selectedClinicalTrial.clinicalTrial;
        data['patient'] = this.state.selectedClinicalTrial.patientId;

        this.setState({displayProgress: true});

        sendAccessRequest(data)
        .then(response => {
            if(!response.ok){
                this.setState({displayProgress: false});
                return;
            }

            this.props.enqueueSnackbar(strings.clinicalTrial.preview.sendRequestSuccess, { variant: 'success' });
            this.closeDetailView();
        })
        .catch(error => {
            console.log(error);
            this.setState({displayProgress: false});
        });
    }

    openDetailView(clinicalTrialPreview){
        this.setState({
            displayDetailView: true, 
            selectedClinicalTrial: clinicalTrialPreview
        });  
    }
    
    closeDetailView(){
        this.setState({displayDetailView: false, displayProgress: false});
    }

    renderClinicalTrialPreview(clinicalTrialPreview){
        const id = clinicalTrialPreview.clinicalTrial;
        const title = "ClinicalTrial: " + id;
        const message = `Date: ${this.renderColumnDate(clinicalTrialPreview.time)}, ClinicalTrialType: ${clinicalTrialPreview.clinicalTrialType}`

        return <Card 
                    key={id}
                    className="accessRequestItem"
                >
                    <CardActionArea>
                        <CardContent>
                        <Typography gutterBottom variant="h5" component="h2">
                            {title}
                        </Typography>
                        <Typography variant="body2" color="textSecondary" component="p">
                            {message}
                        </Typography>
                        </CardContent>
                    </CardActionArea>
                    <CardActions>
                        <Button 
                            size="small" 
                            color="primary" 
                            variant="contained"
                            onClick={() => this.openDetailView(clinicalTrialPreview)}
                        >
                            {strings.tasklist.trialAccessRequest.detail}
                        </Button>
                    </CardActions>
                </Card>
    }

    render() {

        return (
            <div >
                <Grid container >
                    <Grid item xs={6} md={3}>
                        <Paper className='paper'>
                            <Typography 
                                variant="h3" 
                                component="h1" 
                                color="inherit"
                                align="center"
                            >
                                {strings.clinicalTrial.preview.form.search}
                            </Typography>
                        </Paper>

                        <Paper className='paper'>
                            <SearchTrialForm 
                                data={this.state.data}
                                errors={this.state.errors}
                                onSubmit={this.onSearchSubmit}
                                onCancel={this.onSearchCancel}
                                onChange={this.changeData}
                                onChangeCheckBox={this.changeCheckBox}
                                institutionsDatasource={this.state.institutions}
                            />
                        </Paper>
                        
                    </Grid>   
                    <Grid item xs={6} md={9} className="trialsPreviewContainer">
                        {
                            this.state.clinicalTrialPreviewList.map(clinicalTrialPreview => 
                                this.renderClinicalTrialPreview(clinicalTrialPreview)
                            )
                        }
                        {
                            this.state.displayDetailView && 
                                <ClinicalTrialDetail
                                    shouldOpen={this.state.displayDetailView}
                                    clinicalTrial={this.state.selectedClinicalTrial}
                                    onClose={ this.closeDetailView }
                                    onSubmit={ this.sendRequest }
                                    attributesDescription={this.clinicalTrialPreviewDescription}
                                    form={false}
                                    submitLabel={strings.tasklist.trialAccessRequest.sendRequest}
                                    displayProgress={this.state.displayProgress}
                                    displayPdfExportButton={false}
                                    displayResource={false}
                                />
                        }
                    </Grid>
                </Grid>
            <div className="trialPreviewActions">
                    <Pagination 
                        className="pagination"
                        count={this.state.pages} 
                        color="primary" 
                        showFirstButton 
                        showLastButton 
                        size="large" 
                        onChange={this.paginSelect} 
                    />                   
            </div>

            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers }) {
    return { menu: menuReducers, user: authReducers.user };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(ClinicalTrialPreviewList)));
