import * as Actions from '../actions/Actions';

const initialState = {
    loader: false,
    loaderCount: 0
};

const siteDataReducers = (state = initialState, action) => {

    switch (action.type) {

        case Actions.TOGGLE_LOADER:

            let loaderCount = action.loader ? state.loaderCount + 1 : state.loaderCount - 1;

            return {
                ...state,
                loaderCount: loaderCount,
                loader: loaderCount > 0
            };
        case Actions.FORCE_HIDE_LOADER:
            return {
                ...state,
                loaderCount: 0,
                loader: false
            };

        default: return state;
    }
};

export default siteDataReducers