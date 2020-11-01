import React, { Component } from 'react'
import { Link, withRouter } from "react-router-dom";
import { bindActionCreators } from "redux";
import * as Actions from "../actions/Actions";
import MenuState from "../constants/MenuState";
import strings from "../localization";

import { lock, logout } from "../base/Auth";
import connect from "react-redux/es/connect/connect";

import MoreVert from '@material-ui/icons/MoreVert';
import MenuIcon from '@material-ui/icons/Menu';
import PersonIcon from '@material-ui/icons/Person';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AddIcon from '@material-ui/icons/Add';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import HomeIcon from '@material-ui/icons/Home';
import NotificationsIcon from '@material-ui/icons/Notifications';
import FormatListNumberedIcon from '@material-ui/icons/FormatListNumbered';

import { 
    ListItemIcon, ListItemText, 
    Menu, MenuItem,
    IconButton, Tooltip, Badge
} from "@material-ui/core";

class Header extends Component {

    constructor(props) {
        super(props);

        this.state = {
            anchorEl: null,
        }
    }

    /** HANDLERS **/

    handleMenuClick(event) {
        this.setState({ anchorEl: event.currentTarget });
    }

    handleMenuClose() {
        this.setState({ anchorEl: null });
    }

    logout() {
        logout();
        this.props.logout();
        this.props.history.push('/signIn');
    }

    lock() {
        lock();
        this.props.history.push('/');
    }

    getHeaderClass() {

        if (this.props.menu.state === MenuState.SHORT) {
            return 'short';
        }
        else {
            return '';
        }
    }

    render() {

        return (
            <div id='header' className={this.getHeaderClass()}>
                <div className='left'>
                    {
                        this.props.menu.state === MenuState.FULL &&
                        <IconButton size='small' onClick={() => this.props.changeMenuState(MenuState.SHORT)}>
                            <MoreVert />
                        </IconButton>
                    }
                    {
                        this.props.menu.state === MenuState.SHORT &&
                        <IconButton size='small' onClick={() => this.props.changeMenuState(MenuState.FULL)}>
                            <MenuIcon />
                        </IconButton>
                    }
                </div>
                <div className='right'>
                    <Link to={"/"} >
                        <Tooltip title={strings.menu.Home} aria-label="add" arrow>
                            <IconButton color="primary">
                                <HomeIcon />
                            </IconButton>
                        </Tooltip>
                    </Link>
                    <Tooltip title="Add" aria-label="add" arrow>
                        <IconButton color="primary">
                            <AddIcon />
                        </IconButton>
                    </Tooltip>
                    <Link to={"/profile"} >
                        <IconButton color="primary">
                            {strings.profile.profile}<PersonIcon />
                        </IconButton>
                    </Link>
                    
                    <Link to={"/tasks"} >
                        <Tooltip title={strings.menu.Tasks} aria-label="add" arrow>
                            <IconButton color="primary">
                                <FormatListNumberedIcon />
                            </IconButton>
                        </Tooltip>
                    </Link>

                    <Link to={"/"} >
                        <Tooltip title={strings.menu.Notifications} aria-label="add" arrow>
                            <Badge badgeContent={4} color="primary" variant="dot">
                                <IconButton color="primary">
                                    <NotificationsIcon />
                                </IconButton>
                            </Badge>
                        </Tooltip>
                    </Link>

                    <IconButton
                        size="small"
                        color="primary"
                        aria-owns={this.state.anchorEl ? 'person-menu' : undefined}
                        aria-haspopup="true"
                        onClick={(event) => this.handleMenuClick(event)}
                    >
                        <ArrowDropDownIcon />
                    </IconButton>
                    <Menu
                        id='person-menu'
                        anchorEl={this.state.anchorEl}
                        open={Boolean(this.state.anchorEl)}
                        onClose={() => this.handleMenuClose()}
                    >
                        <MenuItem onClick={() => this.logout()}>
                            <ListItemIcon>
                                <ExitToAppIcon />
                            </ListItemIcon>
                            <ListItemText inset primary={strings.header.logout} />
                        </MenuItem>
                    </Menu>
                    
                </div>
            </div>

        );
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({
        changeMenuState: Actions.changeMenuState,
        logout: Actions.logout,
    }, dispatch);
}

function mapStateToProps({ menuReducers, authReducers }) {
    return { menu: menuReducers, user: authReducers.user };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Header));