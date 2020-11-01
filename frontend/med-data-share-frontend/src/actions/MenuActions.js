export const CHANGE_MENU_STATE = '[MENU] CHANGE MENU STATE';
export const CHANGE_FULL_SCREEN = '[MENU] CHANGE FULL SCREEN';

export function changeMenuState(state) {
    return {
        type: CHANGE_MENU_STATE,
        state
    }
}

export function changeFullScreen(fullScreen) {
    return {
        type: CHANGE_FULL_SCREEN,
        fullScreen
    }
}