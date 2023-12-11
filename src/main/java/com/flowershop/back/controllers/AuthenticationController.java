package com.flowershop.back.controllers;

import com.flowershop.back.configuration.UtilsProject;
import com.flowershop.back.configuration.annotations.isValid;
import com.flowershop.back.configuration.enums.Messages;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.LoginResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.ReadersService;
import com.flowershop.back.services.TokenService;
import com.flowershop.back.services.UserService;
import com.flowershop.back.services.impl.TokenServiceImpl;
import com.flowershop.back.services.impl.email.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        String hash = UtilsProject.randomHash();
        String pass = new BCryptPasswordEncoder().encode(data.password());
        User user = this.userService.createUser(data, hash, pass);
        this.userService.save(user);
        this.emailService.sendEmailVerification(data.login(), hash);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }


    @PostMapping("/alter-password/{email}")
    public ResponseEntity<ReturnResponseBody> alterPass(@PathVariable("email") String email){
        this.emailService.sendEmailResetPass(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Enviamos um email para sua conta, indicando o passo a passo para fazer a troca de senha."));

    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "hash", required = false, defaultValue = "null") String hash,
                                                @RequestParam(name = "key", required = false, defaultValue = "null") String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(readersService.fileHtmlConfirmacao(Messages.RESETSENHA.getValue()), headers, HttpStatus.OK);
    }


    @PostMapping("/reset-password/{hash}/{pass}/{key}")
    public ResponseEntity<ReturnResponseBody> reset(@isValid @PathVariable("hash") String hash,
                                                    @isValid @PathVariable("pass") String pass,
                                                    @isValid @PathVariable("key") String key){

        String password = new BCryptPasswordEncoder().encode(pass);
        this.userService.resetPassword(hash, password, key);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Senha alterada"));
    }




}
