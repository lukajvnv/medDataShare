import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField} from "@material-ui/core";

const UserForm = ({
                      onSubmit,
                      onCancel,
                      onChange,
                      errors,
                      data
                  }) => (

    <form id='user-form'>
        <TextField
            label={ strings.userForm.email }
            error={ hasError(errors, 'email') }
            helperText={ getError(errors, 'email') }
            fullWidth
            autoFocus
            name='email'
            onChange={ onChange }
            margin="normal"
            value={ data.email }
        />
        <TextField
            label={ strings.userForm.firstName }
            error={ hasError(errors, 'firstName') }
            helperText={ getError(errors, 'firstName') }
            fullWidth
            autoFocus
            name='firstName'
            onChange={ onChange }
            margin="normal"
            value={ data.firstName }
        />
        <TextField
            label={ strings.userForm.lastName }
            error={ hasError(errors, 'lastName') }
            helperText={ getError(errors, 'lastName') }
            fullWidth
            autoFocus
            name='lastName'
            onChange={ onChange }
            margin="normal"
            value={ data.lastName }
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

export default UserForm;