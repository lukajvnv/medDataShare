import { combineReducers } from 'redux';
import authReducers from './AuthReducers';
import menuReducers from "./MenuReducers";
import siteDataReducers from "./SiteDataReducers";

const appReducers = combineReducers({
    authReducers,
    menuReducers,
    siteDataReducers
});

export default appReducers;