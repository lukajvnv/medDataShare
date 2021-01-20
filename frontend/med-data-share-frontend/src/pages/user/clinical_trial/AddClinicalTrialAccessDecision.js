import React from 'react';

import {getError, hasError} from "../../../functions/Validation";

import strings from "../../../localization";

import {
    Button, Paper, Slide, TextField,
    Dialog, DialogActions, DialogTitle, DialogContent,
    InputLabel, Select, FormHelperText, FormControl, FormControlLabel, Switch, LinearProgress
} from "@material-ui/core";

import AccessType from '../../../constants/AccessType';

const accessTypeDatasource = [
    {key: AccessType.FORBIDDEN, label: 'Disallow access to the trial'},
    {key: AccessType.UNCONDITIONAL, label: 'Allow access to the trial'},
];

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="right" ref={ref} {...props} />;
});

const AddClinicalTrialAccessDecision = ({
    shouldOpen,
    onClose,
    onSubmit,
    onChange,
    onSwitchChange,
    data,
    errors,
    displayProgress
}) => {
 
    return (
        <Dialog
            fullWidth
            // maxWidth = 'md'
            open={shouldOpen}
            onClose={onClose}
            // fullScreen
            aria-labelledby="scroll-dialog-title"
            aria-describedby="scroll-dialog-description"
            TransitionComponent={Transition}
            disableBackdropClick={true}
            disableEscapeKeyDown={true}
        >
            <DialogTitle id="scroll-dialog-title">{strings.tasklist.trialAccessRequest.form.pageTitle}</DialogTitle>
            <DialogContent
                dividers
            >
                <Paper className="paper">
                    <FormControl 
                        fullWidth
                        required
                        error={hasError(errors, 'accessDecision')}
                    >
                        <InputLabel id="accessTypeId">{strings.tasklist.trialAccessRequest.form.decision}</InputLabel>
                        <Select
                            labelId="accessTypeId"
                            onChange={onChange}
                            value={data.accessDecision || ''}
                            native
                            inputProps={{
                                name: "accessDecision",
                            }}
                        >
                            <option aria-label="None" value="" />
                            {
                                accessTypeDatasource.map((accessType) => (
                                    <option key={accessType.key} value={accessType.key}>{accessType.label}</option>
                                ))
                            }
                        </Select>
                        <FormHelperText >{getError(errors, 'accessDecision')}</FormHelperText>
                    </FormControl>
                    <FormControlLabel
                        control={<Switch checked={data.anonymity || false} onChange={onSwitchChange} name="anonymity" />}
                        label={strings.tasklist.trialAccessRequest.form.anonymity}
                    />
                    <TextField
                        label={strings.tasklist.trialAccessRequest.form.from}
                        error={hasError(errors, 'from')}
                        helperText={getError(errors, 'from')}
                        fullWidth
                        autoFocus
                        name='from'
                        type="date"
                        margin="normal"
                        InputLabelProps={{
                            shrink: true,
                        }}
                        value={data.from || undefined}
                        onChange={onChange}
                    />
                    <TextField
                        label={strings.tasklist.trialAccessRequest.form.until}
                        error={hasError(errors, 'until')}
                        helperText={getError(errors, 'until')}
                        fullWidth
                        autoFocus
                        name='until'
                        type="date"
                        margin="normal"
                        InputLabelProps={{
                            shrink: true,
                        }}
                        value={data.until || undefined}
                        onChange={onChange}
                    />
                </Paper>
            </DialogContent>
            {
                displayProgress &&
                    <LinearProgress color="secondary" />
            }
            <DialogActions>
                <Button onClick={onClose} color="secondary">
                    {strings.clinicalTrial.detail.close}
                </Button>
                <Button onClick={onSubmit} color="primary">
                    {strings.tasklist.trialAccessRequest.form.decision}
                </Button>
            </DialogActions>
            {
                displayProgress &&
                    <LinearProgress color="secondary" />
            }
        </Dialog>
    );
}

export default AddClinicalTrialAccessDecision;
