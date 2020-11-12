import { request, requestFile } from "../base/HTTP";
import HttpMethod from "../constants/HttpMethod";
import {getPrimarlyRole} from "../util/UserUtil";

const roledBasedRoutes = {
    'ROLE_COMMON_USER': '/commonUser', 
    'ROLE_DOCTOR': '/doctor', 
    'ROLE_MED_ADMIN': '/doctor', 
    'ROLE_SUPER_ADMIN': '/superAdmin'
}

export async function signUp(data) {
    return await request('/auth/signUp', data, HttpMethod.POST);
}

export async function authTest() {
    return await request('/testAuth');
}

export async function updateUser(data, user) {
    const role = getPrimarlyRole(user);
    const roleBasedRoute = roledBasedRoutes[role];
    return await request(roleBasedRoute, data, HttpMethod.POST);
}

export async function getUser(id) {
    return await request('/user/' + id);
}

export async function getLoggedUser() {
    return await request('/user/current');
}

export async function getClinicalTrialAccessRequests(requestType) {
    requestType = requestType ? "?requestType=" + requestType : "";
    return await request('/user/trialAccessRequest' + requestType);
}

export async function trialAccessRequestDecision(data) {
    return await request('/user/trialAccessRequest', data, HttpMethod.POST);
}

export async function getMedInstitutions(data) {
    return await request('/user/institution', data);
}

export async function getClinicalTrialPreview(data, params) {
    return await request('/user/clinicalTrialPreview', data, HttpMethod.POST, {
        params: params
    });
}

export async function getClinicalTrialInPdf(clinicalTrialId) {
    return await requestFile(`/user/file/${clinicalTrialId}`);
}

export async function getClinicalTrialImage(binaryId) {
    return await requestFile(`/user/image/${binaryId}`);
}

export async function sendAccessRequest(data) {
    return await request('/user/sendAccessRequest', data, HttpMethod.POST);
}

export async function resetPasswordRequest(data) {
    return await request('/api/users/password/reset', data, HttpMethod.POST);
}

export async function resetPassword(data) {
    return await request('/user/resetPassword', data, HttpMethod.POST);
}
