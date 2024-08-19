package com.rest.controller;


import com.rest.config.JwtProvider;
import com.rest.model.Cart;
import com.rest.model.USER_ROLE;
import com.rest.model.User;
import com.rest.repository.CartRepository;
import com.rest.repository.UsersRepository;
import com.rest.request.LoginRequest;
import com.rest.response.AuthResponce;
import com.rest.service.imp.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponce> createUserHandle(@RequestBody User user) throws Exception {
        User isEmail = usersRepository.findByEmail(user.getEmail());

        if (isEmail != null) {
            throw new Exception("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setEmail(user.getFullname());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = usersRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponce authResponce = new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("Register success");
        authResponce.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponce, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponce> signin(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String pass = req.getPassword();

        Authentication authentication = authenticate(username, pass);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponce authResponce = new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("Login success");


        authResponce.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponce, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String pass) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username...");
        }

        if (!passwordEncoder.matches(pass, userDetails.getPassword())) {
            throw new BadCredentialsException(("Invalid Password"));
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
