import React from 'react';
import Page from '../../../common/Page';

import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import {withSnackbar} from "notistack";

import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";

import {
    Paper, Tabs, Tab, Box, 
} from "@material-ui/core";

import GetAppIcon from '@material-ui/icons/GetApp';
import HistoryIcon from '@material-ui/icons/History';
import SendIcon from '@material-ui/icons/Send';
import TaskListPageState from '../../../constants/TaskListPageState';
import ClinicalTrialDefineAccess from './ClinicalTrialDefineAccessList';
import ClinicalTrialAccessRequestList from './ClinicalTrialAccessRequestList';

export class TaskList extends Page {

    tabsDefinition = {
        defineAccess: { label: strings.tasklist.tabs.trialDefineAccess, icon: <GetAppIcon />, value:  TaskListPageState.ClinicalTrialDefineAccess},
        accessRequest: { label: strings.tasklist.tabs.trialAccessRequest, icon: <GetAppIcon />, value:  TaskListPageState.ClinicalTrialAccessRequest },
        accessHistory: { label: strings.tasklist.tabs.trialAccessHistory, icon: <HistoryIcon />, value:  TaskListPageState.ClinicalTrialAccessHistory },
        requestedAccess: {label: strings.tasklist.tabs.requestedTrialAccess, icon: <SendIcon />, value:  TaskListPageState.RequestedTrialAccess }
    }   

    constructor(props) {
        super(props);

        this.props.changeFullScreen(false);

        let currentActiveTab = props.location.state ?
            this.props.location.state.currentActiveTab : TaskListPageState.ClinicalTrialAccessRequest;

        let tabs = [];
        if (this.isCommonUser()){
            tabs = ['defineAccess', 'accessRequest', 'accessHistory', 'requestedAccess'];
        } else {
            tabs = ['requestedAccess'];
            currentActiveTab = TaskListPageState.RequestedTrialAccess
        }

        this.state = {
            currentActiveTab: currentActiveTab,
            tabs: tabs
        }

        this.tabChange = this.tabChange.bind(this);
    }

    tabChange(event, newTabValueIndex){
        const currentActiveTabDefinitionKey = this.state.tabs[newTabValueIndex];
        const tab = this.tabsDefinition[currentActiveTabDefinitionKey];
        const currentActiveTab = tab.value;
        this.setState({currentActiveTab});
    }

    renderTab(tabKey){
        const tab = this.tabsDefinition[tabKey];
        return <Tab key={tab.value} label={tab.label} icon={tab.icon}/>
    }

    render() {
        return (
            <div>
                <Paper
                >
                    <Tabs
                        value={this.state.currentActiveTab}
                        onChange={this.tabChange}
                        indicatorColor="secondary"
                        textColor="secondary"
                        centered
                        style={{backgroundColor: '#374258'}}
                    >
                        {
                            this.state.tabs.map(tab => this.renderTab(tab))
                        }
                    </Tabs>
                </Paper>
                <Box m={2}>
                {
                    this.state.currentActiveTab === TaskListPageState.ClinicalTrialDefineAccess && 
                    <ClinicalTrialDefineAccess />
                }
                {
                    this.state.currentActiveTab === TaskListPageState.ClinicalTrialAccessRequest  && 
                    <ClinicalTrialAccessRequestList 
                        pageTitle={strings.tasklist.trialAccessRequest.pageTitle}
                        requiredDecision={true}
                        shouldDisplayTrial={false}
                        requestType={"requests"}
                    />    
                }
                {
                    this.state.currentActiveTab === TaskListPageState.ClinicalTrialAccessHistory && 
                    <ClinicalTrialAccessRequestList 
                        pageTitle={strings.tasklist.trialAccessHistory.pageTitle}
                        requiredDecision={false}
                        shouldDisplayTrial={false}
                        requestType={"history"}
                    />   
                }
                {
                    this.state.currentActiveTab === TaskListPageState.RequestedTrialAccess  && 
                    <ClinicalTrialAccessRequestList 
                        pageTitle={strings.tasklist.requestedTrialAccess.pageTitle}
                        requiredDecision={false}
                        shouldDisplayTrial={true}
                        requestType={"requested"}
                    />   
                }
                </Box>
            </div>
        )
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(TaskList)));
