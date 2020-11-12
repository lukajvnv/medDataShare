import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField, Select, InputLabel, FormControl, FormHelperText} from "@material-ui/core";


const MedAdminForm = ({
        onSubmit,
        onCancel,
        onChange,
        errors,
        data,
        medInstitutionsDataSource
    }) => (

    <form id='user-form'>
        <TextField
            label={ strings.medAdminForm.email }
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
            label={strings.medAdminForm.password}
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
            label={ strings.medAdminForm.firstName }
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
            label={ strings.medAdminForm.lastName }
            error={ hasError(errors, 'lastName') }
            helperText={ getError(errors, 'lastName') }
            fullWidth
            autoFocus
            name='lastName'
            onChange={ onChange }
            margin="normal"
            value={ data.lastName }
        />
        <FormControl 
            fullWidth
            error={hasError(errors, 'medInstitution')}
        >
            <InputLabel id={strings.medAdminForm.medInstitution}>{strings.medAdminForm.medInstitution}</InputLabel>
            <Select
                labelId={strings.medAdminForm.medInstitution}
                onChange={onChange}
                value={data.value}
                native
                name='medInstitution'
            >
                <option aria-label="None" value="" />
                {
                    medInstitutionsDataSource.map((medInstitutionValue, index) => (
                        <option key={index} value={medInstitutionValue.id}>{medInstitutionValue.name}, {medInstitutionValue.address}</option>
                    ))
                }
            </Select>
            {
                hasError(errors, 'medInstitution') && <FormHelperText >{getError(errors, 'medInstitution')}</FormHelperText>
            }
        </FormControl>
        <div className='submit-container'>
            <Button variant="contained" color="primary" onClick={ onSubmit }>
                { strings.medAdminForm.ok }
            </Button>
            <Button variant="contained" color="secondary" onClick={ onCancel }>
                { strings.medAdminForm.cancel }
            </Button>
        </div>
    </form>
);

export default MedAdminForm;