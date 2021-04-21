import strings from '../localization';

export function locallizeTrialType(clinicalTrialType) {
    const clinicalTrialTypeKey = clinicalTrialType.toLowerCase();
    const clinicalTrialTypeLabel = strings.clinicalTrial.form[clinicalTrialTypeKey];
    
    return clinicalTrialTypeLabel;
}

export function locallizeTrialAccessRights(clinicalTrialAccess) {
    const clinicalTrialAccessLabel = strings.clinicalTrial.accessType[clinicalTrialAccess];
    
    return clinicalTrialAccessLabel;
}

export function locallizeUserGender(userGender) {
    const genderLabel = strings.profile.detail.genders[userGender];
    
    return genderLabel;
}

export function locallizeUserRole(userRole) {
    const userRoleLabel = strings.profile.detail.roles[userRole];
    
    return userRoleLabel;
}

export function renderId(value, characterNumbers = 10){
    return value.substring(0, characterNumbers);
}