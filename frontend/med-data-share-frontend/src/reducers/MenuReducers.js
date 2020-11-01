import * as Actions from '../actions/Actions';
import MenuState from "../constants/MenuState";

const initialState = {
    state: MenuState.FULL,
    fullScreen: false
};

const menuReducers = (state = initialState, action) => {

    switch (action.type) {

        case Actions.CHANGE_MENU_STATE:
            return {
                ...state,
                state: action.state
            };

        case Actions.CHANGE_FULL_SCREEN:
            return {
                ...state,
                fullScreen: action.fullScreen
            };

        default: return state;
    }
};

export default menuReducers;