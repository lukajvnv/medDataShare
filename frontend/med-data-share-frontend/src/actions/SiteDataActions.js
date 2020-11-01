export const TOGGLE_LOADER = '[SITE_DATA] TOGGLE_LOADER';
export const FORCE_HIDE_LOADER = '[SITE_DATA] FORCE_HIDE_LOADER';

export function showLoader() {

    return {
        type: TOGGLE_LOADER,
        loader: true
    };
}

export function forceHideLoader() {
    return {
        type: TOGGLE_LOADER
    };
}

export function hideLoader() {
    return {
        type: TOGGLE_LOADER,
        loader: false
    };
}