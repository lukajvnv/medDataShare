import React, {Component} from 'react';
import { Button } from '@material-ui/core';
import SocialLogin from 'react-social-login';
import FacebookIcon from '@material-ui/icons/Facebook';

class SocialButton extends Component {
 
    render() {
        const {triggerLogin, children} = this.props;
        console.log("usao");
        return (
            <Button onClick={triggerLogin}  variant="contained" color="primary" fullWidth startIcon={<FacebookIcon />}>
              { children }
            </Button>
        );
    }
}
 
export default SocialLogin(SocialButton);