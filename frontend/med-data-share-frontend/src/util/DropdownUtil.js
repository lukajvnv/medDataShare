export function getDropdownOptions(array, name, label, value = undefined) {
    if(!array){
        return;
    }

    return array.map(item => ({
        value: value ? item[value] : item,
        label: item[label],
        target: {
            name: name,
            value: value ? item[value] : item
        }
    }));

}

export function getDropdownOptionsValue(option, value, name = 'name'){

    return value !== undefined && value == option.value[name] ? value : undefined;
}