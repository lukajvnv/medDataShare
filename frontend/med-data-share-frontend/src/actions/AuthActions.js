import {getUserFromLocalStorage} from '../base/Auth';

export const LOGIN = '[AUTH] LOGIN';
export const LOGOUT = '[AUTH] LOGOUT';
export const LOAD_USER = '[AUTH] LOAD';

export function login(user) {
    return {
        type: LOGIN,
        user
    }
}

export function logout(user = undefined) {
    return {
        type: LOGOUT,
        user
    }
}

export function loadUser() {
    return {
        type: LOAD_USER,
        user: getUserFromLocalStorage()
    }
}