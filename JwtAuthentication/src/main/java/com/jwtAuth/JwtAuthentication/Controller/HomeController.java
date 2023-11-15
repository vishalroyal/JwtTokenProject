package com.jwtAuth.JwtAuthentication.Controller;

import com.jwtAuth.JwtAuthentication.Model.JwtModel;
import com.jwtAuth.JwtAuthentication.Model.JwtResponse;
import com.jwtAuth.JwtAuthentication.Security.CustomerUserDetailsService;
import com.jwtAuth.JwtAuthentication.Utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/welcome")
public class HomeController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil helper;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/hello")
    public String welcomeEndpoint(){
        return new String("This is my page");

    }

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtModel jwtModel)
    {

        doAuthenticate(jwtModel.getUsername(), jwtModel.getPassword());

        System.out.println(jwtModel);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtModel.getUsername());
            String token = helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);

            //  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtModel.getUsername(),jwtModel.getPassword()));

        }
        catch (UsernameNotFoundException e)
        {
                e.printStackTrace();
        }

      UserDetails userDetails= customerUserDetailsService.loadUserByUsername(jwtModel.getUsername());

        String token= helper.generateToken(userDetails);
        System.out.println("Token "+token);

        return ResponseEntity.ok(new JwtResponse(token,jwtModel.getUsername()));



    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
