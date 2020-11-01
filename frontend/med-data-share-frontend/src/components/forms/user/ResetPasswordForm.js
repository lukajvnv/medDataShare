import React from 'react';
import strings from '../../../localization';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import {getError, hasError} from "../../../functions/Validation";

const ResetPasswordForm = ({
                              onSubmit,
                              onCancel,
                              onChange,
                              errors,
                              data,
                              keyPress
                          }) => (
    <form id="registration-form" onSubmit={ onSubmit } action = "#">
        <TextField
            label={ strings.resetPassword.password }
            error={ hasError(errors, 'password') }
            helperText={ getError(errors, 'password') }
            fullWidth
            autoFocus
            name='password'
            type='password'
            onChange={ onChange }
            onKeyPress={ keyPress }
            margin="normal"
            value={ data.password || ''}
        />

        <TextField
            label={ strings.resetPassword.passwordRepeat }
            error={ hasError(errors, 'passwordRepeat') }
            helperText={ getError(errors, 'passwordRepeat') }
            fullWidth
            name='passwordRepeat'
            type='password'
            onChange={ onChange }
            onKeyPress={ keyPress }
            margin="normal"
            value={ data.passwordRepeat || ''}
        />

        <div className='submit-container'>
            <Button variant="contained" color="primary" onClick={ onSubmit }>
                { strings.resetPassword.resetPassword }
            </Button>
            <Button variant="contained" color="secondary" onClick={ onCancel }>
                { strings.resetPassword.cancel }
            </Button>
        </div>
    </form>
);

export default ResetPasswordForm;