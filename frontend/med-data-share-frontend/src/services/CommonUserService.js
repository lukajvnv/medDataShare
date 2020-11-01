import { request } from "../base/HTTP";
import HttpMethod from "../constants/HttpMethod";

export async function editClinicalTrial(data) {
    return await request('/commonUser/clinicalTrial', data, HttpMethod.POST);
}

export async function getUserClinicalTrials() {
    return await request('/commonUser/clinicalTrial');
}

export async function getUserDefineAccessTrials(accessType) {
    return await request(`/commonUser/clinicalTrial?accessType=${accessType}`);
}
