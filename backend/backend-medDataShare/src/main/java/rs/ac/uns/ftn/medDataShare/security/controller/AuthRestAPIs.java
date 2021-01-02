package rs.ac.uns.ftn.medDataShare.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.security.dto.ErrorResponse;
import rs.ac.uns.ftn.medDataShare.security.dto.SignUpDto;
import rs.ac.uns.ftn.medDataShare.security.jwt.JwtProvider;
import rs.ac.uns.ftn.medDataShare.security.dto.JwtResponse;
import rs.ac.uns.ftn.medDataShare.security.dto.SignInDto;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/signUp")
    public String signUp(@Valid @RequestBody SignUpDto signUpForm, BindingResult result){
        if(result.hasErrors()){
            throw new AuthException("error while signUp");
        }

        if(userDetailsService.getUser(signUpForm.getEmail()) != null){
            throw new AuthException("user with this username already exsists");
        }

        CommonUser commonUser = CommonUser
                .builder()
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .gender(signUpForm.getGender())
                .birthday(signUpForm.getBirthday())
                .username(signUpForm.getEmail())
                .email(signUpForm.getEmail())
                .password(userPasswordEncoder.encode(signUpForm.getPassword()))
                .enabled(true)
                .role(Constants.ROLE_COMMON_USER)
                .activeSince(new Date())
                .build();
        CommonUser saved = commonUserRepository.save(commonUser);
        return "Success";
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDto loginRequest) {
        User userDb = userDetailsService.getUser(loginRequest.getUsername());

        if(userDb == null) {
			return new ResponseEntity<>(new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
		}

    	if(!userDb.isEnabled()) {
			return new ResponseEntity<>(new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
		}

    	Authentication authentication = null;
		try {

			authentication = authenticationManager.authenticate(
			            new UsernamePasswordAuthenticationToken(
			                    loginRequest.getUsername(),
			                    loginRequest.getPassword()
			            )
			    );
		} catch (AuthenticationException e) {
			System.out.println("ne validan");
			return new ResponseEntity<>(new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
		}

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Get auth: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), userDb.getId()));
    }
}
