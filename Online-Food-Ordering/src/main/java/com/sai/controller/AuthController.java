package com.sai.controller;


import com.sai.config.JwtProvider;
import com.sai.model.Cart;
import com.sai.model.USER_ROLE;
import com.sai.model.User;
import com.sai.repository.CartRepository;
import com.sai.repository.UserRepository;
import com.sai.request.LoginRequest;
import com.sai.response.AuthResponse;
import com.sai.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;

     @Autowired
     private JwtProvider jwtProvider;

     @Autowired
     private CustomerUserDetailsService customerUserDetailsService;

     @Autowired
     private CartRepository cartRepository;

     @PostMapping("/signup")
     public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

          User isEmailExist  = userRepository.findByEmail(user.getEmail());
          if(isEmailExist!=null){
               throw new Exception("Email is already used with account");
          }

          User createdUser = new User();
          createdUser.setEmail(user.getEmail());
          createdUser.setFullName(user.getFullName());
          createdUser.setRole(user.getRole());
          createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

          User savedUser = userRepository.save(createdUser);

          Cart cart = new Cart();
          cart.setCustomer(savedUser);
          cartRepository.save(cart);

          Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
          SecurityContextHolder.getContext().setAuthentication(authentication);

          String jwt = jwtProvider.generateToken(authentication);

          AuthResponse authResponse = new AuthResponse();
          authResponse.setJwt(jwt);
          authResponse.setMessage("Registration Success");
          authResponse.setRole(savedUser.getRole());

          return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
     }

     @PostMapping("/signin")
     public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){

          String userName = req.getEmail();
          String password = req.getPassword();

          Authentication authentication = authenticate(userName, password);

          Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
          String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

          String jwt = jwtProvider.generateToken(authentication);

          AuthResponse authResponse = new AuthResponse();
          authResponse.setJwt(jwt);
          authResponse.setMessage("Login Success");
          authResponse.setRole(USER_ROLE.valueOf(role));

          return new ResponseEntity<>(authResponse, HttpStatus.OK);
     }

     private Authentication authenticate(String userName, String password) {

          UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
          if(userDetails==null){
               throw new BadCredentialsException("Invalid UserName.....");
          }
          if(!passwordEncoder.matches(password, userDetails.getPassword())){
               throw new BadCredentialsException("Inavlid Password....");
          }
          return new UsernamePasswordAuthenticationToken(userDetails,null,
                  userDetails.getAuthorities());
     }
}
