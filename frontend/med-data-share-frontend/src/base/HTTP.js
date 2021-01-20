import CONFIG from '../config';
import AuthenticationTypes from "../constants/AuthenticationTypes";
import HttpMethod from '../constants/HttpMethod';
import history from '../history';
import { clearUserData } from './Auth';
import axios from 'axios';
import strings from '../localization';

const authenticatedApiRoutes = [
    '/auth/signIn', 
    '/testAuth/token/validate',
];

const Axios = (function () {

    let instance;

    function createInstance() {
        return axios.create({
            baseURL: CONFIG.baseUrl
        });
    }

    return {
        getInstance: function () {

            if (!instance) {
                instance = createInstance();
            }

            if (getTokenType()) {
                instance.defaults.headers.common['Authorization'] = getToken();
            }
            instance.all = axios.all;

            return instance;
        }
    }
})();

Axios.getInstance().interceptors.response.use(response => {

    response.ok = response.status >= 200 && response.status < 300;

    return response;
}, async error => {

    if(!error.response){
        history.push('/error');
    }

    const { response: { status, data } } = error;

    if (status === 404) {

        history.push('/not-found');
    }
    else if (status === 500) {

        history.push({
            pathname: '/error',
            state   : { msg: `url:${data.callerUrl}, message'${data.message}'`}
        });
        // history.push('/error');
    }
    else if (status === 401) {
        const apiRequestUrl = error.config.url;
        if(authenticatedApiRoutes.indexOf(apiRequestUrl) < 0){
            history.push({
                pathname: '/forbidden',
                state   : { msg: strings.forbidden.forbidden }
            });
        }   
    }
    else if (status === 403) {

        clearUserData();
        history.push('/');
        return error;
    }

    return error;
});

export async function request(url, data = [], method = HttpMethod.GET, options = {}) {

    return await connect(url, data, method, options);
}

export async function requestFile(url, data = [], method = HttpMethod.GET) {

    try {

        let tokenType = getTokenType();

        let headers = {
            'Access-Control-Allow-Credentials': 'true',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Authorization',
            'Accept': 'application/json'
        };

        let responseType = 'blob';

        if (tokenType) {
            headers['Authorization'] = getToken();
        }

        // params
        const options = {
            headers : headers, 
            responseType:  responseType
        }

        return await connect(CONFIG.baseUrl + url, data, method, options);
    } catch (error) {
        history.push("/error");
    }
}

export async function submitFormData(url, data = [], method = HttpMethod.GET) {

    try {

        let tokenType = getTokenType();

        let headers = {
            'Access-Control-Allow-Credentials': 'true',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Authorization',
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data'
        };

        if (tokenType) {
            headers['Authorization'] = getToken();
        }

        return await connect(CONFIG.baseUrl + url, data, method, { headers });
    } catch (error) {
        history.push("/error");
    }
}

export async function connect(url, data, method, options) {

    switch (method) {
        case HttpMethod.GET: {
            return await Axios.getInstance().get(url + makeParametersList(data), options);
        }
        case HttpMethod.POST: return Axios.getInstance().post(url, data, options);
        case HttpMethod.PUT: return Axios.getInstance().put(url, data, options);
        case HttpMethod.DELETE: return Axios.getInstance().delete(url, options);
        default: return;
    }
}

export function makeParametersList(parameters) {
    let parametersList = `?`;

    Object.keys(parameters).map((key, index) => (
        parametersList += parameters[key] ? `${key}=${parameters[key]}&` : ''
    ));

    parametersList = parametersList.slice(0, -1);

    return parametersList === '?' ? '' : parametersList;
}

export function getToken() {

    if (getTokenType() === AuthenticationTypes.BearerToken) {
        return 'Bearer ' + localStorage.getItem(CONFIG.tokenKey);
    }
    else if (getTokenType() === AuthenticationTypes.SocialToken) {
        return 'Social ' + localStorage.getItem(CONFIG.socialTokenKey);
    } 
    
    return '';
}

export function getUserFromLocalStorage() {

    let user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
}

function getTokenType() {

    if (localStorage.getItem(CONFIG.tokenKey)) {
        return AuthenticationTypes.BearerToken;
    }
    else if (localStorage.getItem(CONFIG.socialTokenKey)) {
        return AuthenticationTypes.SocialToken
    }

    return null;
}