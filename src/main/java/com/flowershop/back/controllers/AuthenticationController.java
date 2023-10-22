package com.flowershop.back.controllers;

import com.flowershop.back.configuration.UtilsProject;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.LoginResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.UserService;
import com.flowershop.back.services.impl.TokenServiceImpl;
import com.flowershop.back.services.impl.email.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenServiceImpl tokenServiceImpl;
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    UserService userService;



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){

        String hash = this.userService.validateUser(data);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        var token = tokenServiceImpl.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(token , hash ));

    }


    @PostMapping("/register")
    public ResponseEntity<ReturnResponseBody> register(@RequestBody @Valid AuthenticationDTO data){
        String hash = UtilsProject.randomHash();
        String pass = new BCryptPasswordEncoder().encode(data.password());
        User user = this.userService.createUser(data, hash, pass);
        this.userService.save(user);

        this.emailService.sendEmailVerification(data.login(), hash);

        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }


}
