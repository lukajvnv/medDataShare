import {Component} from 'react'
import {getUserFromLocalStorage, logout } from "../base/Auth";
import UserRole from '../constants/UserRole';
import {isUserRole} from "../util/UserUtil";

class BaseComponent extends Component {

    constructor(props) {
        super(props);

        this.logout = this.logout.bind(this);
    }

    isUserLoggedIn() {
        return getUserFromLocalStorage() != null;
    }

    getUser() {

        return getUserFromLocalStorage();
    }

    logout() {

        logout();
        this.props.logout();
    }

    removeFromArray(array, item) {

        let index = array.indexOf(item);

        if(index === -1) {
            return false;
        }

        array.splice(index, 1);

        return true;
    }

    isInArray(array, item) {

        return array.indexOf(item) !== -1;
    }

    isMedAdmin() {

        if(!this.props.user) {
            return false;
        }

        return isUserRole(this.props.user, UserRole.MED_ADMIN);
    }

    isSuperAdmin() {

        if(!this.props.user) {
            return false;
        }

        return isUserRole(this.props.user, UserRole.ADMIN);
    }
}

export default BaseComponent;