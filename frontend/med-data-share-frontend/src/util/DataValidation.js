export function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

export function isFloat(n) {
    return !isNaN(parseFloat(n));
}

export function isInt(n) {
    return isFinite(n)
}

export function isLink(link) {

    let regularExpretion = /^(ftp|http|https):\/\/[^ "]+$/;

    return regularExpretion.test(link);
}

export function isBoolean(value){
    return typeof(value) === 'boolean';
}