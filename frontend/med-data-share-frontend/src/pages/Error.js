import React, {Component} from 'react'
import Button from '@material-ui/core/Button';
import {bindActionCreators} from "redux";
import * as Actions from "../actions/Actions";
import {Link, withRouter} from "react-router-dom";
import connect from "react-redux/es/connect/connect";
import strings from "../localization";


class Error extends Component {

    constructor(props) {
        super(props);

        this.props.changeFullScreen(true);
    }


    render() {

        return (

            <div id='error'>
                <h1>500</h1>
                <h3>{ strings.error.error }</h3>

                <Link to={'/'}>
                    <Button variant="contained" color="secondary">
                        { strings.error.dashboard }
                    </Button>
                </Link>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers })
{
    return { menu: menuReducers };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Error));