import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField} from "@material-ui/core";
import { locallizeUserGender, locallizeUserRole } from '../../../../util/ClinicalTrialUtil';

const transformationFunctions = {
    renderUserRole: (value) => locallizeUserRole(value),
    renderGender: (value) => locallizeUserGender(value)
}

const EditUserForm = ({
    onSubmit,
    onCancel,
    onChange,
    errors,
    data,
    dataMeta,
    nonEditableFieldList,
    localizedAttributeLabels
}) => (
    <form id='user-form'>
        {
            Object.keys(dataMeta).map(attribute => {
                let value = data[attribute];

                const transformation = localizedAttributeLabels[attribute].transform;
                if(transformation){
                    value = transformationFunctions[transformation](value);
                }
                return <TextField
                    key={attribute}
                    disabled = {nonEditableFieldList.includes(attribute)}
                    label={localizedAttributeLabels[attribute].label}
                    error={ hasError(errors, attribute) }
                    helperText={ getError(errors, attribute) }
                    fullWidth
                    autoFocus
                    name={attribute}
                    onChange={ onChange }
                    margin="normal"
                    value={ value }
                />
            })
        }
        <div className='submit-container'>
            <Button variant="contained" color="primary" onClick={ onSubmit }>
                { strings.userForm.ok }
            </Button>
            <Button variant="contained" color="secondary" onClick={ onCancel }>
                { strings.userForm.cancel }
            </Button>
        </div>
    </form>
);

export default EditUserForm;