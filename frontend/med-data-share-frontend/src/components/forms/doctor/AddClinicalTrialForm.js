import React from 'react';
import strings from "../../../localization";
import {getError, hasError} from "../../../functions/Validation";
import ClinicalTrialType from '../../../constants/ClinicalTrialType';

import {
    Button, TextField, 
    Input, InputAdornment,
    FormControl, InputLabel, Select, FormHelperText 
} from "@material-ui/core";
import CloudUploadIcon from '@material-ui/icons/CloudUpload';

const clinicalTrialDatasource = [
    {key: ClinicalTrialType.CT, label: strings.clinicalTrial.form.ct},
    {key: ClinicalTrialType.RTG, label: strings.clinicalTrial.form.rtg},
    {key: ClinicalTrialType.US, label: strings.clinicalTrial.form.us},
    {key: ClinicalTrialType.CBC, label: strings.clinicalTrial.form.cbc}
];

const FileUploader = ({file, onFileUpload}) => {
    return (
        <Input 
            id="my-input" 
            name="file"
            aria-describedby="my-helper-text" 
            // onChange={onFileUpload}
            // type="file"
            startAdornment={
                <InputAdornment position="start">
                    <Button
                        color="primary"
                        size="small"
                        component="label"
                        variant="outlined"
                        // style={{width: 500}}
                        startIcon={<CloudUploadIcon />}
                    >
                    {strings.clinicalTrial.form.file}
                    <input
                        type="file"
                        style={{ display: "none" }}
                        onChange={onFileUpload}
                    />
                    </Button>
                </InputAdornment>
            }
            value={file ? file.name : ''}

        />        
    );
};

const AddClinicalTrialForm = ({
    onSubmit,
    onFileUpload,
    keyPress,
    onCancel,
    onChange,
    errors,
    data
}) => (

    <form id='clinicalTrial-form'>
        {/* <TextField
            label={strings.clinicalTrial.form.patient}
            error={hasError(errors, 'patient')}
            helperText={getError(errors, 'patient')}
            disabled
            required
            fullWidth
            name='patient'
            margin="normal"
            value={data.patient || ''}
        /> */}
        <FormControl 
            className="formControl"
            required
            error={hasError(errors, 'clinicalTrialType')}
        >
            <InputLabel id="dl">{strings.clinicalTrial.form.clinicalTrialType}</InputLabel>
            <Select
                labelId="dl"
                onChange={onChange}
                value={data.clinicalTrialType || ''}
                native
                inputProps={{
                    name: "clinicalTrialType",
                }}
            >
                <option aria-label="None" value="" />
                {
                    clinicalTrialDatasource.map((clinicalTrial) => (
                        <option key={clinicalTrial.key} value={clinicalTrial.key}>{clinicalTrial.label}</option>
                    ))
                }
            </Select>
            <FormHelperText >{getError(errors, 'clinicalTrialType')}</FormHelperText>
        </FormControl>
        <TextField
            label={strings.clinicalTrial.form.introduction}
            error={hasError(errors, 'introduction')}
            helperText={getError(errors, 'introduction')}
            fullWidth
            autoFocus
            name='introduction'
            onChange={onChange}
            onKeyPress={keyPress}
            margin="normal"
            value={data.introduction || ''}
            multiline
            rows='3'
            required
        />
        <TextField
            label={strings.clinicalTrial.form.relevantParameters}
            error={hasError(errors, 'relevantParameters')}
            helperText={getError(errors, 'relevantParameters')}
            fullWidth
            autoFocus
            name='relevantParameters'
            onChange={onChange}
            onKeyPress={keyPress}
            margin="normal"
            value={data.relevantParameters || ''}
            multiline
            rows='3'
        />
        <TextField
            label={strings.clinicalTrial.form.conclusion}
            error={hasError(errors, 'conclusion')}
            helperText={getError(errors, 'conclusion')}
            fullWidth
            autoFocus
            name='conclusion'
            onChange={onChange}
            onKeyPress={keyPress}
            margin="normal"
            value={data.conclusion || ''}
            multiline
            rows='3'
            required
        />
        {/* <FormControl 
            fullWidth
            error={hasError(errors, 'file')}
        >
            <InputLabel htmlFor="my-input">{strings.clinicalTrial.form.file}</InputLabel>
            <Input 
                id="my-input" 
                name="file"
                aria-describedby="my-helper-text" 
                onChange={onFileUpload}
                type="file"
            />
            <FormHelperText id="my-helper-text">{hasError(errors, 'file') ? getError(errors, 'conclusion') : strings.clinicalTrial.form.filePlaceholder}</FormHelperText>
        </FormControl> */}
        <FormControl 
            // fullWidth
            error={hasError(errors, 'file')}
        >
            {/* <InputLabel htmlFor="my-input">{strings.clinicalTrial.form.file}</InputLabel> */}
            <FileUploader file={data.file} onFileUpload={onFileUpload}/>
            <FormHelperText id="my-helper-text">{hasError(errors, 'file') ? getError(errors, 'conclusion') : strings.clinicalTrial.form.filePlaceholder}</FormHelperText>
        </FormControl>
        <div className='submit-container'>
            <Button variant="contained" color="primary" onClick={ onSubmit }>
                { strings.clinicalTrial.form.submit }
            </Button>
            {/* <Button variant="contained" color="secondary" onClick={ onCancel }>
                { strings.userForm.cancel }
            </Button> */}
        </div>
    </form>
);

export default AddClinicalTrialForm;