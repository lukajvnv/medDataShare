import React from 'react';
import strings from '../../../localization';
import { getError, hasError } from "../../../functions/Validation";

import {Button, TextField, Box, Grid} from "@material-ui/core";
import { Link } from "react-router-dom";

const SignInForm = ({
  onSubmit,
  onChange,
  errors,
  data,
  keyPress
}) => (
    <form id="login-form" onSubmit={onSubmit} action="#">
      {/* <Box mt={2} border={1} borderRadius={16} className="mediaContainer">
        <Grid >
          <Box m={2}>
            <Grid item >
              <Typography variant="h6" component="h6" gutterBottom align="center">
                {strings.login.other}
              </Typography>
            </Grid>
          </Box>
          <Box mx={4}>
            <Grid item >
              <SocialLogin />
            </Grid>
          </Box>
          <Box mt={2}>

          </Box>

        </Grid>
      </Box> */}

      <TextField
        label={strings.login.email}
        error={hasError(errors, 'email')}
        helperText={getError(errors, 'email')}
        fullWidth
        autoFocus
        name='email'
        onChange={onChange}
        onKeyPress={keyPress}
        margin="normal"
        value={data.email || ''}
      />

      <TextField
        label={strings.login.password}
        error={hasError(errors, 'password')}
        helperText={getError(errors, 'password')}
        fullWidth
        name='password'
        type='password'
        onChange={onChange}
        onKeyPress={keyPress}
        margin="normal"
        value={data.password || ''}
      />

      <Box mt={2}>
        <Grid>
          <Box mt={2}>
            <Grid item >
              <Link href="#" to={'/#'} onClick={(event) => event.preventDefault()}>
                {strings.login.forgetPasswordMsg}
              </Link>
            </Grid>
          </Box>
          <Box mt={1}>
            <Grid item >
              <Link to={'/sign-up'}  >
                {strings.login.signUpMsg}
              </Link>
            </Grid>
          </Box>

        </Grid>
      </Box>

      <div className='submit-container'>
        <Button variant="contained" color="primary" onClick={onSubmit}>
          {strings.login.login}
        </Button>
      </div>
    </form>
  );

export default SignInForm;