import CONFIG from '../config';
import HttpMethod from '../constants/HttpMethod';
import { request } from './HTTP';
import history from '../history';
import { isUserOneOfRoles } from "../util/UserUtil";
import {decode} from "jsonwebtoken";
import { timeIntervalInMilliseconds } from '../util/DateUtil';

export async function login(username, password) {

    clearUserData();

    let data = {
        username: username,
        password: password
    };

    return await request('/auth/signIn', data, HttpMethod.POST).then((response) => {

        if (!response.ok) {
            return response;
        }

        setTokenToLocalStorage(response.data.token);
        const token = decode(response.data.token);
        const issuedAt = token.iat;
        const expiresIn = token.exp;

        setTimeout(()=> clearUserData(), timeIntervalInMilliseconds(issuedAt, expiresIn));
        
        if (isUserOneOfRoles(response.data, CONFIG.rolesAllowed)) {
            const roles = response.data.roles;
            const username = response.data.username;
            setUserToLocalStorage({roles, username})
        }
        else {

            clearUserData();
            response.ok = false;

        }
        return response;
    }
    ).catch(error => {
        console.log(error)
    });
}

export async function unlock(username, password) {

    clearUserDataLock();

    let data = {
        username: username,
        password: password
    };

    return await request('/oauth/token', data, HttpMethod.GET).then((response) => {

        if (!response.ok) {
            return response;
        }

        setTokenToLocalStorage(response.data.access_token, response.data.refresh_token);

        return response;
    }
    );
}


export async function refreshToken(refreshToken) {

    let data = {
        client_id: CONFIG.clientId,
        client_secret: CONFIG.clientSecret,
        grant_type: 'refresh_token',
        refresh_token: refreshToken
    };

    return await request('/oauth/token', data, HttpMethod.GET).then((response) => {

        if (response.data && response.data.access_token && response.data.refresh_token) {
            setTokenToLocalStorage(response.data.access_token, response.data.refresh_token);
        }

        return true;
    }
    );
}

export async function validateToken() {

    return await request('/testAuth/token/validate').then((response) => {

        console.log(response);
        if(!response.ok){
            console.log('not okay');
            response.ok = false;
            clearUserData();
        } 

        return response;
    }
    ).catch(error => {
        console.log(error);
    })
    .finally(() => {
        
    });
}

export function logout() {
    clearUserData();
    history.push("/");
}

export function lock() {
    clearUserDataLock();
    history.push("/");
}

/** LOCAL STORAGE  **/

export function setUserToLocalStorage(user) {
    localStorage.setItem('user', JSON.stringify(user));
}


export function getUserFromLocalStorage() {
    let user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
}


export function setTokenToLocalStorage(access_token) {
    localStorage.setItem(CONFIG.tokenKey, access_token);
}

export function getRefreshToken() {
    return localStorage.getItem(CONFIG.refreshTokenKey);
}

export function getToken() {
    return localStorage.getItem(CONFIG.tokenKey);
}

export function setSocialTokenToLocalStorage(access_token) {
    localStorage.setItem(CONFIG.socialTokenKey, access_token);
}

export function clearUserData() {
    localStorage.removeItem('user');
    clearUserDataLock();
}

export function updateUserData(user) {
    localStorage.removeItem('user');
    setUserToLocalStorage(user);
}

function clearUserDataLock() {
    localStorage.removeItem(CONFIG.tokenKey);
}

export function isUserLoggedIn() {
    return getUserFromLocalStorage() != null && getToken() != null;
}

export function isUserLocked() {
    return getUserFromLocalStorage() && !getToken();
}