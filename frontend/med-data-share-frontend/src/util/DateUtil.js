import moment from "moment";
import strings from '../localization';

export function getYears(plusYears = 0) {

    let result = [];
    let currentYear = (new Date()).getFullYear();

    currentYear += plusYears;

    for (let i = currentYear; i >= currentYear - 99; i--) {

        result.push({
            name: i,
            value: i
        });
    }

    return result;
}

export function getMonths() {

    return [
        { id: 1, value: strings.months.jan },
        { id: 2, value: strings.months.feb },
        { id: 3, value: strings.months.mar },
        { id: 4, value: strings.months.apr },
        { id: 5, value: strings.months.may },
        { id: 6, value: strings.months.jun },
        { id: 7, value: strings.months.jul },
        { id: 8, value: strings.months.aug },
        { id: 9, value: strings.months.sep },
        { id: 10, value: strings.months.oct },
        { id: 11, value: strings.months.nov },
        { id: 12, value: strings.months.dec },
    ];
}

export function leapYear(year) {
    return ((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0);
}

export function stringToDate(date, format = 'DD-MM-YYYY') {
    return moment(date, format);
}

export function dateToString(date, format = 'DD-MM-YYYY') {
    return moment(date).format(format);
}

export function dateToStringDatePickerFormat(date, format = 'MM/DD/YYYY') {
    return moment(date).format(format);
}

export function dateTimeToString(date, format = 'DD-MM-YYYY HH:MM') {
    return moment(date).format(format);
}

export function splitDatePartFromString(date) {
    return date ? date.split('T')[0] : '';
}

export function reformatDate(rawStringDate) {
    if (rawStringDate) {
        const date = new Date(splitDatePartFromString(rawStringDate))
        return dateToString(date);
    }
}

export function farFromNow(dateString) {
    let date = new Date(dateString);

    const dateMoment = moment(date);
    const now = moment();

    const timeDiff = now.diff(dateMoment, 'days')
    if (timeDiff < 1) {
        const duration = moment.duration(now.diff(dateMoment));
        if(duration.asHours() < 1) {
            const durationAsMinutes = Math.floor(duration.asMinutes());
            if(durationAsMinutes < 1) {
                const seconds = Math.floor(duration.asSeconds());
                return (seconds > 0 ? seconds: 0) + ' seconds';
            } else {
                return durationAsMinutes + ' minutes ';
            }
        } else {
            return Math.floor(duration.asHours()) + ' hours ';
        }
    } else {
        return timeDiff + ' days ';
    }
}

export function timeIntervalInMilliseconds(from, to){
    return (to - from) * 1000;
}

export function createDate(string, format = moment.ISO_8601){
    return moment(string, format, true);
}

export function isDate(date){
    return createDate(date).isValid();
}

export function transformDate(stringDate){
    const dateParts = stringDate.split("-");
    return dateParts.reverse().join('-');
}

export function isValidDate(stringDate){
    if(stringDate.split('-').length !== 3){
        return false;
    }
    const date = createDate(transformDate(stringDate), "YYYY-MM-DD", true);
    return date.isValid() && date.isBefore();
}

export function dateBefore(from, to){
    return from.isBefore(to);
}
