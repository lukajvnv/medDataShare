import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField} from "@material-ui/core";

const DoctorForm = ({
        onSubmit,
        onCancel,
        onChange,
        errors,
        data,
    }) => (

    <form id='user-form'>
        <TextField
            label={ strings.doctorForm.email }
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
            label={strings.doctorForm.password}
            error={hasError(errors, 'password')}
            helperText={getError(errors, 'password')}
            fullWidth
            name='password'
            type='password'
            onChange={onChange}
            margin="normal"
            value={data.password}
        />
        <TextField
            label={ strings.doctorForm.firstName }
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
            label={ strings.doctorForm.lastName }
            error={ hasError(errors, 'lastName') }
            helperText={ getError(errors, 'lastName') }
            fullWidth
            autoFocus
            name='lastName'
            onChange={ onChange }
            margin="normal"
            value={ data.lastName }
        />
        <TextField
            label={ strings.doctorForm.occupation }
            error={ hasError(errors, 'occupation') }
            helperText={ getError(errors, 'occupation') }
            fullWidth
            autoFocus
            name='occupation'
            onChange={ onChange }
            margin="normal"
            value={ data.occupation }
        />
        <div className='submit-container'>
            <Button variant="contained" color="primary" onClick={ onSubmit }>
                { strings.doctorForm.ok }
            </Button>
            <Button variant="contained" color="secondary" onClick={ onCancel }>
                { strings.doctorForm.cancel }
            </Button>
        </div>
    </form>
);

export default DoctorForm;