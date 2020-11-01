import * as Actions from '../actions/Actions';

const initialState = {
    user: undefined
};

const authReducers = (state = initialState, action) => {

    switch (action.type) {

        case  Actions.LOGIN:
        case Actions.LOAD_USER:
            return {
                ...state,
                user: action.user
            };
        case Actions.LOGOUT:
            return {
                ...state,
                user: undefined
            };

        default: return state;
    }
};

export default authReducers;