import React from 'react';
import strings from "../../../../localization";
import {getError, hasError} from "../../../../functions/Validation";
import {Button, TextField} from "@material-ui/core";

const EditUserForm = ({
                      onSubmit,
                      onCancel,
                      onChange,
                      errors,
                      data,
                      dataMeta,
                      nonEditableFieldList
                  }) => (

    <form id='user-form'>
        {
            Object.keys(dataMeta).map(attribute => {
                    return <TextField
                                key={attribute}
                                disabled = {nonEditableFieldList.includes(attribute)}
                                label={ attribute }
                                error={ hasError(errors, attribute) }
                                helperText={ getError(errors, attribute) }
                                fullWidth
                                autoFocus
                                name={attribute}
                                onChange={ onChange }
                                margin="normal"
                                value={ data[attribute] }
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