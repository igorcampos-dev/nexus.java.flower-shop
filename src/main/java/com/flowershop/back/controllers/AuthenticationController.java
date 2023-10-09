package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.configuration.Utils;
import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.LoginResponseDTO;
import com.flowershop.back.domain.user.RegisterDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.security.TokenService;
import com.flowershop.back.services.email.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository repository;
    @Autowired
    TokenService tokenService;
    @Autowired
    EmailService emailService;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        UserDetails userDetails = repository.findByLogin(data.login());
            User user = (User) userDetails;
            if (user.getStatus() == StatusUser.P) return ResponseEntity.badRequest().body(new ResponseMessage("Usuário está pendente, verfique seu email para validar,ou converse com nosso suporte!"));
           var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
           var auth = this.authenticationManager.authenticate(usernamePassword);

           var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token , user.getHash()));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid RegisterDTO data){
        String hash = Utils.randomHash();
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().body(new ResponseMessage("Usuário já existe!"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, Role.USER, StatusUser.P, hash);

        this.repository.save(newUser);

        boolean sendEmail = emailService.sendEmailVerification(data.login(), hash);
        if (!sendEmail) return ResponseEntity.badRequest().body(new ResponseMessage("Não foi possivel enviar email para o remetente"));
        return ResponseEntity.ok().body(new ResponseMessage("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }
}
