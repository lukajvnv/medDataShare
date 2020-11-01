import React from 'react'
import FormComponent from "./FormComponent";
import {dateToString} from "../util/DateUtil";
import DoneIcon from '@material-ui/icons/Done';
import CloseIcon from '@material-ui/icons/Close';

class Page extends FormComponent {

    params = [];
    page = undefined;

    constructor(props) {
        super(props);

        this.setPage = this.setPage.bind(this);
    }

    componentWillMount() {

        this.loadParams();

        this.fetchData();
    }

    componentWillReceiveProps(props) {

        if(!this.needRefresh()) {
            return;
        }

        this.fetchData();
    }

    // componentDidMount() {

    //     this.loadParams();

    //     this.fetchData();
    // }

    // componentDidUpdate() {
    //     if(!this.needRefresh()) {
    //         return;
    //     }

    //     this.fetchData();
    // }

    fetchData() {

    }

    getSearchParam(param) {

        let url = new URL(window.location);

        return url.searchParams.get(param);
    }

    loadParams(data = 'searchData') {

        let loadedData = {};

        for(let param of this.params) {

            let paramString = this.getSearchParam(param.name);
            let value;

            if(!paramString || paramString === '') {

                if(this.props[param.name]) {
                    loadedData[param.name] = this.props[param.name];
                }
                else {
                    loadedData[param.name] = param.defaultValue;
                }

                continue;
            }

            if(paramString.includes(',')) {

                value = [];

                for(let item of paramString.split(',')) {

                    if(this.isNumeric(item)) {
                        if(this.isFloat(item)) {
                            value.push(parseFloat(item));
                        }
                        else if(this.isInt(item)) {
                            value.push(parseInt(item));
                        }
                    }
                }
            }
            else if(this.isNumeric(paramString)) {

                if(this.isFloat(paramString)) {
                    value = parseFloat(paramString);
                }
                else if(this.isInt(paramString)) {
                    value = parseInt(paramString);
                }
            }
            else {
                value = paramString;
            }

            loadedData[param.name] = value;
        }

        this.state[data] = loadedData;
    }

    buildParams(data = 'searchData') {

        let result = '?';
        let hasParams = false;

        for(let param of this.params) {

            if(this.state[data][param.name]) {
                result += this.buildParam(param, this.state[data], hasParams);
                hasParams = true;
            }
        }

        return hasParams ? result : '';
    }

    buildParam(param, data, hasParams) {

        let result = param.name + '=';

        if(hasParams) {
            result = '&' + result;
        }

        if(Array.isArray(data[param.name])) {
            result += data[param.name].join();
        }
        else {
            result += data[param.name]
        }

        return result;
    }

    needRefresh(data = 'searchData') {

        const oldParams = JSON.parse(JSON.stringify(this.state[data]));

        this.loadParams(data);

        for(const key of Object.keys(this.state[data])) {

            if(oldParams[key] !== this.state[data][key]){
                return true;
            }
        }

        return false;
    }

    setPage(page) {

        this.state.searchData.page = page;

        this.props.history.push(this.page.path + this.buildParams());

        this.fetchData();
    }

    /** RENDER TRANSFORMS */

    renderColumnDeleted(value) {
        return value ? <DoneIcon/> : <CloseIcon/>;
    }

    renderColumnDate(value) {
        return dateToString(value);
    }
}

export default Page;