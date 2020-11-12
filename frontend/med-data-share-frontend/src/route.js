import React from 'react';
import Home from './pages/Home';
import Error from "./pages/Error";
import Forbidden from "./pages/Forbidden";
import NotFound from "./pages/NotFound";
import SignIn from "./pages/user/account/SignIn";
import SignUp from "./pages/user/account/SignUp";  
import UserList from "./pages/admin/users/UserList";
import DoctorList from "./pages/admin/med_institution/DoctorList";
import { Route } from 'react-router-dom';
import UserRole from "./constants/UserRole";
import { isUserOneOfRoles } from './util/UserUtil';
import SuperAdminPortal from './pages/admin/SuperAdminPortal';
import Profile from './pages/user/profile/Profile';
import ClinicalTrial from './pages/user/clinical_trial/ClinicalTrials';
import AddClinicalTrial from './pages/doctor/AddClinicalTrial';
import TaskList from './pages/user/task/TaskList';
import ClinicalTrialAccessRequest from './pages/user/task/ClinicalTrialAccessRequest';
import ClinicalTrialPreviewList from './pages/user/ClinicalTrialPreviewList';

let ROUTES = {
    Home: {
        path: '/',
        component: <Home />,
        auth: true
    },
    Error: {
        path: '/error',
        component: <Error />,
        auth: false
    },
    Forbidden: {
        path: '/forbidden',
        component: <Forbidden />,
        auth: false
    },
    NotFound: {
        path: '/not-found',
        component: <NotFound />,
        auth: false
    },
    SignIn: {
        path: '/signIn',
        component: <SignIn />,
        auth: false
    },
    SignUp: {
        path: '/sign-up',
        component: <SignUp />,
        auth: false
    },
    SuperAdminPortal: {
        path: '/superAdmin',
        component: <SuperAdminPortal  />,
        auth: true,
        roles: [UserRole.ROLE_SUPER_ADMIN]
    },
    Profile: {
        path: '/profile',
        component: <Profile  />,
        auth: true,
    },
    TaskList: {
        path: '/tasks',
        component: <TaskList  />,
        auth: true,
        roles: [UserRole.USER, UserRole.DOCTOR]
    },
    ClinicalTrialAccessRequest: {
        path: '/accessRequest',
        component: <ClinicalTrialAccessRequest  />,
        auth: true,
    },
    ClinicalTrial: {
        path: '/trials',
        component: <ClinicalTrial  />,
        auth: true,
    },
    ClinicalTrialPreviewList: {
        path: '/trialsPreview',
        component: <ClinicalTrialPreviewList  />,
        auth: true,
    },
    AddClinicalTrial: {
        path: '/addClinicalTrial',
        component: <AddClinicalTrial  />,
        auth: true,
        roles: [UserRole.DOCTOR]
    },
    DoctorList: {
        path: '/doctors',
        component: <DoctorList  />,
        auth: true,
        roles: [UserRole.ROLE_MED_ADMIN]
    },
    UserList: {
        path: '/users',
        component: <UserList  />,
        auth: true,
        roles: [UserRole.ADMIN]
    },
};

export default ROUTES;

function getRoute(path) {

    for (const value of Object.values(ROUTES)) {

        if (value.path === path) {
            return value;
        }
    }

    return null;
}

export function checkPath(path) {

    let pathObject = getRoute(path);

    if (!pathObject) {
        return true;
    }

    if (pathObject.auth) {
        return pathObject.auth;
    }

    return false;
}

export function hasProprieteRole(path, user){
    if(!user){
        return true;
    }

    let pathObject = getRoute(path);

    if(!pathObject){
        return false;
    }

    if(!pathObject.roles){
        return true;
    } else {
        return isUserOneOfRoles(user, pathObject.roles);
    }
}

export function getRoutes() {

    let result = [];

    for (const value of Object.values(ROUTES)) {

        result.push(
            <Route key={'route-' + result.length} exact path={value.path} render={() => (
                value.component
            )} />
        )
    }

    return result;
}