import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import {isUserRole} from '../util/UserUtil';

import strings from "../localization";
import MenuState from "../constants/MenuState";
import UserRole from '../constants/UserRole';

import HomeIcon from '@material-ui/icons/Home';
import AddIcon from '@material-ui/icons/Add';
import PersonIcon from '@material-ui/icons/Person';
import FindInPageIcon from '@material-ui/icons/FindInPage';
import FormatListNumberedIcon from '@material-ui/icons/FormatListNumbered';
import ViewListIcon from '@material-ui/icons/ViewList';
import MenuBookIcon from '@material-ui/icons/MenuBook';

import { 
    Drawer, IconButton, 
    List, ListItem, ListItemIcon, ListItemText
} from "@material-ui/core";

class Navigation extends Component {

    navItemsBase = [
        {
            path: '/', icon: <HomeIcon />, text: strings.menu.Home
        },
        {
            path: '/profile', icon: <PersonIcon />, text: strings.menu.Profile
        }
        // ,{
        //     path: '/notifications', icon: <NotificationsIcon />, text: strings.menu.Notifications
        // }
    ]

    navItemsUser = [
        {
            path: '/tasks', icon: <FormatListNumberedIcon />, text: strings.menu.Tasks
        },
        {
            path: '/trials', icon: <MenuBookIcon />, text: strings.menu.Trials
        },
        {
            path: '/trialsPreview', icon: <FindInPageIcon />, text: strings.menu.TrialsPreview
        },
    ]

    navItemsDoctor = [
        {
            path: '/tasks', icon: <FormatListNumberedIcon />, text: strings.menu.Tasks
        },
        {
            path: '/addClinicalTrial', icon: <AddIcon />, text: strings.menu.AddMedicalExamination
        },
        {
            path: '/trialsPreview', icon: <FindInPageIcon />, text: strings.menu.TrialsPreview
        }
    ]

    navItemsMedAdmin = [
        {
            path: '/doctors', icon: <ViewListIcon />, text: strings.menu.AdminPortal
        }
    ]

    navItemsSuperAdmin = [
        {
            path: '/superAdmin', icon: <ViewListIcon />, text: strings.menu.AdminPortal
        }
    ]



    constructor(props) {
        super(props);

        this.state = {

            submenu: {
                example: false
            }
        };
    }

    getNavigationClass() {

        if (this.props.menu.state === MenuState.SHORT) {
            return 'navigation-content-container short';
        }
        else {
            return 'navigation-content-container'
        }
    }

    isCurrentPath(path) {
        return this.props.history.location.pathname === path;
    }

    toggleSubmenu(key) {

        let submenu = this.state.submenu;

        submenu[key] = !submenu[key];

        this.setState({
            submenu: submenu
        });
    }

    renderNavItem(navItem, index){
        return <Link key={index} to={navItem.path} className={this.isCurrentPath(navItem.path) ? 'navigation-link active' : 'navigation-link'} >
            <ListItem className='navigation-item'>

                <ListItemIcon className='navigation-icon'>
                    {navItem.icon}
                </ListItemIcon>

                <ListItemText inset primary={navItem.text} className='navigation-text' />

            </ListItem>
        </Link>
    }

    render() {
        this.navItems = this.navItemsBase;

        if(isUserRole(this.props.user, UserRole.USER)) {
            this.navItems = this.navItems.concat(this.navItemsUser);
        }

        if(isUserRole(this.props.user, UserRole.DOCTOR)) {
            this.navItems = this.navItems.concat(this.navItemsDoctor);
        }

        if(isUserRole(this.props.user, UserRole.ROLE_MED_ADMIN)) {
            this.navItems = this.navItems.concat(this.navItemsMedAdmin);
        }
        
        if(isUserRole(this.props.user, UserRole.ROLE_SUPER_ADMIN)) {
            this.navItems = this.navItems.concat(this.navItemsSuperAdmin);
        }

        return (
            <Drawer variant="permanent" id='navigation'>

                <div className={this.getNavigationClass()}>
                    <div className='logo-container'>
                        <div className='logo'>
                            <Link to={'/'}  >
                                <IconButton>
                                    <FindInPageIcon />
                                </IconButton>
                            </Link>
                        </div>
                        <div className='title'>
                            <h2>{strings.appName}</h2>
                        </div>
                    </div> 
                    
                    <List component="nav">
                        {
                            this.navItems.map((navItem, index) => this.renderNavItem(navItem, index))
                        }
                    </List>
                </div>



            </Drawer>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({}, dispatch);
}

function mapStateToProps({ menuReducers, authReducers }) {
    return { menu: menuReducers, user: authReducers.user };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Navigation));