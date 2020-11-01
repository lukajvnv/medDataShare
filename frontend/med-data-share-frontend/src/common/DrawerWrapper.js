import React, {Component} from 'react'

import {Grid, IconButton} from "@material-ui/core";
import  ChevronRight from "@material-ui/icons/ChevronRight"

class DrawerWrapper extends Component {

    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {

        const { children } = this.props;

        return (

            <Grid className='drawer-wrapper'>
                <div className='drawer-wrapper-header'>
                    <IconButton onClick={ () => this.props.onBack() }>
                        <ChevronRight/>
                    </IconButton>
                </div>
                <div className='drawer-wrapper-content'>
                    { children }
                </div>
            </Grid>
        );
    }
}

export default DrawerWrapper;