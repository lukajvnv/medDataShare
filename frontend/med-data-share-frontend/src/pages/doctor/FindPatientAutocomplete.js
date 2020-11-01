import React from 'react';
import Autosuggest from 'react-autosuggest';
import strings from '../../localization';
import { dateToString } from '../../util/DateUtil';
  
const AddPatientAutocomplete = ({
    setPatient,
    users
}) => {
    const [value, setValue] = React.useState('');
    const [suggestions, setSuggestions] = React.useState([]);

    const getSuggestions = value => {
        const inputValue = value.trim().toLowerCase();
        const inputLength = inputValue.length;
      
        return inputLength === 0 ? [] : users.filter(user =>
            user.firstName.toLowerCase().slice(0, inputLength) === inputValue ||
            user.lastName.toLowerCase().slice(0, inputLength) === inputValue
        );
    };
      
    const getSuggestionValue = suggestion => {
        console.log(suggestion);
        setPatient(suggestion.id);
        return `${suggestion.firstName} ${suggestion.lastName}`;
    };
      
    const renderSuggestion = suggestion => (
        <div>
            {suggestion.firstName} {suggestion.lastName}, {dateToString(suggestion.birthday)}
        </div>
    );


    const onChange = (event, { newValue }) => {
        setValue(newValue);
    };

    const onSuggestionsFetchRequested = ({ value }) => {
        setSuggestions(getSuggestions(value));
    };

    const onSuggestionsClearRequested = () => {
        setSuggestions([]);
    };

    const inputProps = {
        placeholder: strings.clinicalTrial.patientDialog.desc,
        value,
        onChange
    };

    return (
        <Autosuggest
            suggestions={suggestions}
            onSuggestionsFetchRequested={onSuggestionsFetchRequested}
            onSuggestionsClearRequested={onSuggestionsClearRequested}
            getSuggestionValue={getSuggestionValue}
            renderSuggestion={renderSuggestion}
            inputProps={inputProps}
        />
    );
    
}

export default AddPatientAutocomplete;