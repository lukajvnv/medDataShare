import React from 'react';
import Page from '../../../common/Page';

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";
import {getClinicalTrialAccessRequests} from '../../../services/UserService';

import * as Actions from "../../../actions/Actions";
import strings from "../../../localization";

import {
    Card, CardActionArea, CardContent, Typography, Button, CardActions
} from "@material-ui/core";

  
class ClinicalTrialAccessList extends Page {

    constructor(props) {
        super(props);

        this.state = {
            trialAccessRequests: [],
            displayDetailView: false,
            selectedClinicalTrial: undefined,
            clinicalTrials: [],
            data: props.data ? props.data : {},
            errors: {},
            noMatchLabel: false
        }

        this.props.changeFullScreen(false);

        this.viewClinicalRequest = this.viewClinicalRequest.bind(this);
    }

    fetchData(){
        getClinicalTrialAccessRequests(this.props.requestType) 
        .then(response => {
            if(!response.ok){

                return;
            }
            console.log(response);
            const trialAccessRequests = response.data;
            const noMatchLabel = trialAccessRequests.length === 0 ? true : false;
            this.setState({trialAccessRequests: trialAccessRequests, noMatchLabel: noMatchLabel});            
        })
        .catch(error => {
            console.log(error);
        });
    }

    viewClinicalRequest(clinicalTrialRequest){
        this.props.history.push({
            pathname: '/accessRequest',
            state: {
                clinicalTrialRequest: clinicalTrialRequest, 
                requiredDecision: this.props.requiredDecision,
                shouldDisplayTrial: this.props.shouldDisplayTrial,
                currentTab: this.props.currentActiveTab,
                requestType: this.props.requestType
            }
        });
    }

    renderAccessRequest(accessRequest){
        const id = accessRequest.id;
        const title = `${strings.tasklist.trialAccessRequest.term}: ${this.renderId(id)}`;
        const message = `${strings.tasklist.trialAccessRequest.date}: ${this.renderColumnDate(accessRequest.time)}, ${strings.clinicalTrial.preview.clinicalTrialType}: ${this.renderTrialType(accessRequest.clinicalTrialType)}`
        return <Card 
                    key={id}
                    className="accessRequestItem"
                    style={{maxWidth: "100vw"}}
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
                            onClick={() => this.viewClinicalRequest(accessRequest)}
                        >
                            {strings.tasklist.trialAccessRequest.detail}
                        </Button>
                    </CardActions>
                </Card>
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
                    {this.props.pageTitle}
                </Typography>
                <div
                    className="accessRequest"
                >
                    {
                        this.state.trialAccessRequests.map(trialAccessRequest => this.renderAccessRequest(trialAccessRequest))
                    }
                    {
                        this.state.noMatchLabel && <h1>{strings.tasklist.trialAccessRequest.noMatch}</h1>
                    }
                    {/* <Card 
                        className="accessRequestItem"
                    >
                        <CardActionArea>
                            <CardContent>
                            <Typography gutterBottom variant="h5" component="h2">
                                Lizard
                            </Typography>
                            <Typography variant="body2" color="textSecondary" component="p">
                                Lizards are 
                            </Typography>
                            </CardContent>
                        </CardActionArea>
                        <CardActions>
                            <Button size="small" color="primary" variant="contained">
                                {strings.tasklist.trialAccessRequest.detail}
                            </Button>
                        </CardActions>
                    </Card> */}
                    
                    {/* <Card 
                        className="accessRequestItem"
                        style={{maxWidth: "60vw"}}
                    >
                        <CardActionArea>
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="h2">
                                    Lizard
                                </Typography>
                                <Typography variant="body2" color="textSecondary" component="p">
                                    Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging
                                    across all continents except Antarctica
                                </Typography>
                                <Grid container spacing={2} >
                                    <Grid item xs={6} md={6}>
                                        <Paper className='paper'>
                                            <Typography variant="body1" color="textSecondary" component="h5">
                                                fdfdfddfdffd
                                            </Typography>
                                        </Paper>
                                    </Grid>
                                    <Grid item xs={6} md={6}>
                                        <Paper className='paper'>
                                            <Typography variant="body1" color="textPrimary" component="h5">
                                                ffdfdfdfdfd
                                            </Typography>
                                        </Paper>
                                    </Grid>
                                </Grid>
                            </CardContent>
                        </CardActionArea>
                        <CardActions>
                            <Button size="small" color="primary" variant="contained">
                                {strings.tasklist.trialAccessRequest.detail}
                            </Button>
                        </CardActions>
                    </Card> */}
                </div>
               
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(ClinicalTrialAccessList)));
