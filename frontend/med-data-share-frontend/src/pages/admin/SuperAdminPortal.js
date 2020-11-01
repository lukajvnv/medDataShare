import React from 'react';
import Page from '../../common/Page';

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import connect from "react-redux/es/connect/connect";

import strings from "../../localization";
import * as Actions from "../../actions/Actions";
import SuperAdminPortalPageState from "../../constants/SuperAdminPortalPageState";

import {Paper, Tabs, Tab, Box} from '@material-ui/core';

import MedInstitutionList from './med_institution/MedInstitutionList';
import MedAdminList from './med_institution/MedAdminList';

import PeopleIcon from '@material-ui/icons/People';
import ApartmentIcon from '@material-ui/icons/Apartment';

class SuperAdminPortal extends Page {

    constructor(props) {
        super(props);

        this.props.changeFullScreen(false);

        this.state = {
            currentActiveTab: SuperAdminPortalPageState.Institutions
        }

        this.tabChange = this.tabChange.bind(this);

    }

    tabChange(event, newTabValueIndex){
        const currentActiveTab = newTabValueIndex;
        this.setState({currentActiveTab});
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
                        <Tab label={strings.medInstitutionList.pageTitle} icon={<ApartmentIcon />}/>
                        <Tab label={strings.medAdminList.pageTitle} icon={<PeopleIcon />} />
                    </Tabs>
                    <Box m={2}>
                    {
                        this.state.currentActiveTab === SuperAdminPortalPageState.Institutions && 
                            <MedInstitutionList />
                    }
                    {
                        this.state.currentActiveTab === SuperAdminPortalPageState.InstitutionsAdmin && 
                            <MedAdminList />
                    }
                    </Box>
                </Paper>
            </div>
        )
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

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(SuperAdminPortal));
