import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField} from "@material-ui/core";

const MedInstitutionForm = ({
                      onSubmit,
                      onCancel,
                      onChange,
                      errors,
                      data
                  }) => (

    <form id='user-form'>
        <TextField
            label={ strings.medInstitutionForm.name }
            error={ hasError(errors, 'name') }
            helperText={ getError(errors, 'name') }
            fullWidth
            autoFocus
            name='name'
            onChange={ onChange }
            margin="normal"
            value={ data.name }
        />
        <TextField
            label={ strings.medInstitutionForm.address }
            error={ hasError(errors, 'address') }
            helperText={ getError(errors, 'address') }
            fullWidth
            autoFocus
            name='address'
            onChange={ onChange }
            margin="normal"
            value={ data.address }
        />
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

export default MedInstitutionForm;