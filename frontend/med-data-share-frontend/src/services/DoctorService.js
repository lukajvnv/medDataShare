import { request, uploadFile } from "../base/HTTP";
import HttpMethod from "../constants/HttpMethod";

export async function addClinicalTrial(data) {
    return await uploadFile('/doctor/clinicalTrial', data, HttpMethod.POST);
}

export async function getPatients() {
    return await request('/doctor/patients');
}
