import UserRole from "../constants/UserRole";

export function isUserRole(user, role) {

    if(!user){
        return false;
    }

    if(!user.roles || user.roles.length === 0) {
        return false;
    }

    for(let userRole of user.roles) {

        if(userRole['authority'] === role) {
            return true;
        }
    }

    return false;
}

export function isUserOneOfRoles(user, roles) {

    for(let role of roles) {

        if(isUserRole(user, role)) {
            return true;
        }
    }

    return false;
}

export function getPrimarlyRole(user) {

    if(isUserRole(user, UserRole.USER)) {
        return UserRole.USER;
    } else if(isUserRole(user, UserRole.DOCTOR)) {
        return UserRole.DOCTOR;
    } else if(isUserRole(user, UserRole.ROLE_MED_ADMIN)) {
        return UserRole.ROLE_MED_ADMIN;
    } else if(isUserRole(user, UserRole.ROLE_SUPER_ADMIN)) {
        return UserRole.ROLE_SUPER_ADMIN;
    }
}