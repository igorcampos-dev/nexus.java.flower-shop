package com.flowershop.back.controllers;

import com.flowershop.back.configuration.UtilsProject;
import com.flowershop.back.configuration.annotations.isValid;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.LoginResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.ReadersService;
import com.flowershop.back.services.TokenService;
import com.flowershop.back.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenServiceImpl;
    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    ReadersService readersService;

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
        String hash = UtilsProject.randomHash(48);
        String pass = new BCryptPasswordEncoder().encode(data.password());
        User user = this.userService.createUser(data, hash, pass);
        this.userService.save(user);
        this.emailService.sendEmailVerification(data.login(), hash);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }


    @PostMapping("/alter-password/{email}/{hash}")
    public ResponseEntity<ReturnResponseBody> alterPass(@PathVariable("email") @Email @isValid String email, @PathVariable("hash") @isValid String hash){
        this.emailService.sendEmailResetPass(email, hash);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Enviamos um email para sua conta, indicando o passo a passo para fazer a troca de senha."));

    }
}
