import React from 'react';
import strings from '../../../localization';
import { getError, hasError } from "../../../functions/Validation";

import {
    Button, TextField, Box, 
    Radio, RadioGroup, 
    FormControlLabel, FormControl, FormLabel
} from "@material-ui/core";

const SignUpForm = ({
    onSubmit,
    onChange,
    onChangeButtonGroup,
    errors,
    data,
    keyPress
}) => (
        <form id="signUp-form" onSubmit={onSubmit} action="#">
            <TextField
                label={strings.signUp.firstName}
                error={hasError(errors, 'firstName')}
                helperText={getError(errors, 'firstName')}
                fullWidth
                autoFocus
                name='firstName'
                onChange={onChange}
                onKeyPress={keyPress}
                margin="normal"
                value={data.firstName}
            />

            <TextField
                label={strings.signUp.lastName}
                error={hasError(errors, 'lastName')}
                helperText={getError(errors, 'lastName')}
                fullWidth
                autoFocus
                name='lastName'
                onChange={onChange}
                onKeyPress={keyPress}
                margin="normal"
                value={data.lastName}
            />

            <TextField
                label={strings.signUp.email}
                error={hasError(errors, 'email')}
                helperText={getError(errors, 'email')}
                fullWidth
                autoFocus
                name='email'
                onChange={onChange}
                onKeyPress={keyPress}
                margin="normal"
                value={data.email}
            />

            <TextField
                label={strings.signUp.password}
                error={hasError(errors, 'password')}
                helperText={getError(errors, 'password')}
                fullWidth
                name='password'
                type='password'
                onChange={onChange}
                onKeyPress={keyPress}
                margin="normal"
                value={data.password}
            />

            <TextField
                label={strings.signUp.address}
                error={hasError(errors, 'address')}
                helperText={getError(errors, 'address')}
                fullWidth
                autoFocus
                name='address'
                onChange={onChange}
                onKeyPress={keyPress}
                margin="normal"
                value={data.address}
            />

            <TextField
                label={strings.signUp.birthday}
                error={hasError(errors, 'birthday')}
                helperText={getError(errors, 'birthday')}
                fullWidth
                autoFocus
                name='birthday'
                type="date"
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                }}
                value={data.birthday}
                onChange={onChange}
                onKeyPress={keyPress}
            />
            <Box mt={2}>
                <FormControl component="div" fullWidth >
                    <FormLabel component="span">{strings.profile.gender}</FormLabel>
                    <RadioGroup aria-label="gender" name="gender" value={data.gender} onChange={onChangeButtonGroup} row>
                        <FormControlLabel value="Female" control={<Radio />} label={strings.signUp.gender.female} />
                        <FormControlLabel value="Male" control={<Radio />} label={strings.signUp.gender.male} />
                        <FormControlLabel value="Other" control={<Radio />} label={strings.signUp.gender.other} />
                    </RadioGroup>
                </FormControl>
            </Box>
            <div className='submit-container'>
                <Button variant="contained" color="primary" onClick={onSubmit}>
                    {strings.signUp.signUp}
                </Button>
            </div>
        </form>
    );

export default SignUpForm;