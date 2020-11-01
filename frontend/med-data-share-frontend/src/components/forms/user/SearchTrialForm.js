import React from 'react';
import strings from "../../../localization";
import {getError, hasError} from "../../../functions/Validation";
import {
    Button, TextField, Checkbox,
    FormControl, InputLabel, Select, FormControlLabel,
} from "@material-ui/core";

import RefreshIcon from '@material-ui/icons/Refresh';
import SearchIcon from '@material-ui/icons/Search';
import ClinicalTrialType from '../../../constants/ClinicalTrialType';

const clinicalTrialTypeDatasource = [
    {key: ClinicalTrialType.CBC, label: 'Complete blood count'},
    {key: ClinicalTrialType.CT, label: 'CT'},
    {key: ClinicalTrialType.RTG, label: 'RTG'},
    {key: ClinicalTrialType.US, label: 'Ultrasound'},
];

const orderByDatasource = [
    {key: 'latest', label: 'Order by latest'},
    {key: 'oldest', label: 'Order by oldest'},
];


const SearchTrialForm = ({
    onSubmit,
    onCancel,
    onChange,
    onChangeCheckBox,
    data,
    errors,
    institutionsDatasource
}) => {

    const renderSelectOptions = (datasource) => {
        return datasource.map((option) => (
                    <option key={option.key} value={option.key}>{option.label}</option>
        ))    
    }
    
    return (
        <form id='user-form'>
            <FormControl 
                fullWidth
            >
                <InputLabel id="clinicalTrialTypeId">{strings.clinicalTrial.preview.form.clinicalTrialType}</InputLabel>
                <Select
                    labelId="clinicalTrialTypeId"
                    onChange={onChange}
                    value={data.clinicalTrialType || ''}
                    native
                    inputProps={{
                        name: "clinicalTrialType",
                    }}
                >
                    <option aria-label="None" value="" />
                    {
                        renderSelectOptions(clinicalTrialTypeDatasource)
                    }
                </Select>
            </FormControl>
            <FormControl 
                fullWidth
            >
                <FormControlLabel
                    control={<Checkbox checked={data.relevantParameters || false} onChange={onChangeCheckBox} name="relevantParameters" />}
                    label={strings.clinicalTrial.preview.form.relevantParameters}
                />
            </FormControl>
            <TextField
                label={strings.clinicalTrial.preview.form.from}
                fullWidth
                autoFocus
                error={hasError(errors, 'from')}
                helperText={getError(errors, 'from')}
                name='from'
                type="date"
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                }}
                value={data.from || ""}
                onChange={onChange}
            />
            <TextField
                label={strings.clinicalTrial.preview.form.until}
                fullWidth
                autoFocus
                error={hasError(errors, 'until')}
                helperText={getError(errors, 'until')}
                name='until'
                type="date"
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                }}
                value={data.until || ""}
                onChange={onChange}
            />
            <FormControl 
                fullWidth
            >
                <InputLabel id="institutionsId">{strings.clinicalTrial.preview.form.institutions}</InputLabel>
                <Select
                    labelId="institutionsId"
                    onChange={onChange}
                    value={data.institutions || ''}
                    native
                    inputProps={{
                        name: "institutions",
                    }}
                >
                    <option aria-label="None" value="" />
                    {
                        institutionsDatasource.map((institution) => (
                            <option key={institution.key} value={institution.key}>{institution.label}</option>
                        ))
                    }
                </Select>
            </FormControl>
            <FormControl 
                fullWidth
            >
                <InputLabel id="orderById">{strings.clinicalTrial.preview.form.orderBy}</InputLabel>
                <Select
                    labelId="orderById"
                    onChange={onChange}
                    value={data.orderBy || ''}
                    native
                    inputProps={{
                        name: "orderBy",
                    }}
                >
                    <option aria-label="None" value="" />
                    {
                        renderSelectOptions(orderByDatasource)
                    }
                </Select>
            </FormControl>

            
            <div className='search-submit-container'>
                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={ onSubmit }
                    startIcon={<SearchIcon />}
                >
                    {strings.clinicalTrial.preview.form.search }
                </Button>
                <Button 
                    variant="contained" 
                    color="secondary" 
                    onClick={ onCancel }
                    startIcon={<RefreshIcon />}
                >
                    {strings.clinicalTrial.preview.form.cancel }
                </Button>
            </div>
        </form>
    );
}

export default SearchTrialForm;