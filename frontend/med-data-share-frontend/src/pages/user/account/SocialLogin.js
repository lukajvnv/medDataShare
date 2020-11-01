import React from 'react';
import config  from '../../config.js';
import SocialButton from './SocialButton';

const handleSocialLogin = (user) => {
  console.log(user)
}
 
const handleSocialLoginFailure = (err) => {
  console.error(err)
}

const SocialLogin = () => {
    return (
     <SocialButton
        provider='facebook'
        appId={config.facebookAppId}
        onLoginSuccess={handleSocialLogin}
        onLoginFailure={handleSocialLoginFailure}
      >
        Login with Facebook
      </SocialButton>
    );
  }

export default SocialLogin;