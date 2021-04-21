import React from 'react'

import { bindActionCreators } from "redux";
import { withRouter } from "react-router-dom";
import {withSnackbar} from "notistack";
import connect from "react-redux/es/connect/connect";
import {getLoggedUser} from '../../../services/UserService';
import { isDate } from '../../../util/DateUtil';
import { isBoolean } from '../../../util/DataValidation';

import {
    Card, CardActionArea, CardActions, CardContent, CardMedia,
    Box, Grid, Paper, Typography, Button
} from '@material-ui/core';

import noImage from '../../../assets/no_image.jpg';
import * as Actions from "../../../actions/Actions";
import strings from "../../../localization";
import PageState from '../../../constants/PageState';
import Page from '../../../common/Page';

import EditUser from '../../admin/users/EditUser';
import ResetPassword from '../../user/account/ResetPassword';

import ContactMailIcon from '@material-ui/icons/ContactMail';
import RotateLeftIcon from '@material-ui/icons/RotateLeft';
import EditIcon from '@material-ui/icons/Edit';

class Profile extends Page {
    localizedAttributeLabels = {
        email: {label: strings.profile.detail.email},
        firstName: {label: strings.profile.detail.firstName},
        lastName: {label: strings.profile.detail.lastName},
        address: {label: strings.profile.detail.address},
        enabled: {label: strings.profile.detail.enabled},
        gender: {label: strings.profile.detail.gender, transform: 'renderGender'},
        birthday: {label: strings.profile.detail.birthday},
        activeSince: {label: strings.profile.detail.activeSince},
        role: {label: strings.profile.detail.role, transform: 'renderUserRole'},
        occupation: {label: strings.profile.detail.occupation},
        medInstitution: {label: strings.profile.detail.institution},
        id: {label: strings.profile.detail.id}
    };
    constructor(props) {
        super(props);

        this.state = {
            imageAnchorEl: undefined,
            user: undefined,
            displayUser: undefined,
            pageProfileState: PageState.View
        }

        this.refresh = this.refresh.bind(this);
        this.onFinish = this.onFinish.bind(this);
    }

    refresh() {
        this.setState({pageProfileState: PageState.View})
    }

    onFinish(fetch = true) {
        this.refresh();

        setTimeout(()=> {
            this.fetchData()
        }, 1000);
    }

    fetchData(){
        getLoggedUser()
        .then(response => {
            console.log(response);
            const user = response.data;
            const displayUser = {};
            console.log(Object.keys(user));
            for (const [key, value] of Object.entries(user)) {
                console.log('attr:' + user[key]);
                
                if(value){
                    if(value instanceof Object) {
                        displayUser[key] = value['id'];   
                    } else if(isDate(value)){
                        displayUser[key] = this.renderColumnDate(value);   
                    } else {
                        displayUser[key] = value;   
                    }
                } 
            }
            this.setState({displayUser, user});
        })
        .catch(error => {

        });
    }

    renderOneProfileAttribute(attributeKeyValuePair) {
        let [attributeName, attributeValue] = attributeKeyValuePair;
        attributeValue = isBoolean(attributeValue) ? this.renderColumnDeleted(attributeValue): attributeValue;
        if(attributeName === 'id') return '';
        
        let value = attributeValue;
        const transformationFunction = this.localizedAttributeLabels[attributeName].transform;
        if(transformationFunction){
            value = this[transformationFunction](value);
        }

        return <Grid key={attributeName} container spacing={2} >
            <Grid item xs={3} md={2}>    
            </Grid>
            <Grid item xs={3} md={4}>
                <Paper className='paper'>
                    <Typography variant="body1" color="textSecondary" component="h5">
                        {this.localizedAttributeLabels[attributeName].label}
                    </Typography>
                </Paper>
            </Grid>
            <Grid item xs={3} md={4}>
                <Paper className='paper'>
                    <Typography variant="body1" color="textPrimary" component="h5">
                        {value}
                    </Typography>
                </Paper>
            </Grid>
            <Grid item xs={3} md={2}>
            </Grid>  
        </Grid>  
    }

    render() {
        const imageSrc = noImage;

        if(!this.state.displayUser){
            return '';
        }
        return (
            <div>
                <Card className="mediaContainer">
                    <CardActionArea>
                        <CardMedia
                            className="media"
                            image={imageSrc}
                            title="Contemplative Reptile"
                        />
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                {strings.profile.pageTitle}
                            </Typography>
                        </CardContent>
                    </CardActionArea>
                    <CardActions>
                        <Button
                            size="small"
                            color="primary"
                            startIcon={<ContactMailIcon />}
                            variant="outlined"
                            onClick={() => this.setState({pageProfileState: PageState.View})}
                        >
                            {strings.profile.information}
                        </Button>
                        <Button
                            size="small"
                            color="primary"
                            startIcon={<EditIcon />}
                            variant="outlined"
                            onClick={() => this.setState({pageProfileState: PageState.Edit})}
                        >
                            {strings.profile.edit}
                        </Button>
                        <Button
                            size="small"
                            color="primary"
                            startIcon={<RotateLeftIcon />}
                            variant="outlined"
                            onClick={() => this.setState({pageProfileState: PageState.Add})}
                        >
                            {strings.profile.resetPassword}
                        </Button>
                    </CardActions>
                </Card>
                <Box mt={2}>
                    <Card className="mediaContainer">
                    {
                        this.state.pageProfileState === PageState.View &&
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                {strings.profile.basicInfoTitle}
                            </Typography>
                            {
                                Object.entries(this.state.displayUser).map(keyValuePair => this.renderOneProfileAttribute(keyValuePair))
                            }
                        </CardContent>
                    }
                    {
                        this.state.pageProfileState === PageState.Edit && 
                        <EditUser 
                            onCancel={ this.refresh } 
                            onFinish={ this.onFinish } 
                            medInstitution={this.state.medInstitutionId}
                            medInstitutionName={this.state.medInstitutionName} 
                            data={this.state.displayUser}
                            dataMeta={this.state.user}
                            localizedAttributeLabels={this.localizedAttributeLabels}
                        />
                    }
                    {
                        this.state.pageProfileState === PageState.Add && 
                        <ResetPassword 
                            onCancel={ this.refresh } 
                            onFinish={ this.onFinish } 
                        />
                    }
                    </Card>   
                </Box>
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

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(Profile)));
