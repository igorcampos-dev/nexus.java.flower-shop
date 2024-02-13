package com.nexus.back.controllers;

import com.nexus.back.domain.Response;
import com.nexus.back.domain.dto.user.AuthenticationDTO;
import com.nexus.back.domain.dto.user.UserDTO;
import com.nexus.back.domain.dto.user.UserLogin;
import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.Role;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.back.services.EmailService;
import com.nexus.back.services.UserService;
import com.nexus.security.service.jwt.JwtService;
import com.nexus.utils.Utils;
import com.nexus.validations.NonNullOrBlank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/shop")
@Tag(name = "Autenticação do usuário", description = "(Rotas para o usuário fazer autenticação de login, registro, etc)")
public class LoginController {

    private final JwtService tokenService;
    private final EmailService emailService;
    private final UserService userService;
    private final Utils utils;

    @Operation(summary = "efetua o login")
    @PostMapping("/login")
    public ResponseEntity<UserLogin> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informações para login") @RequestBody @Valid AuthenticationDTO data) {

        String hash = this.userService.getHash(data);
        UserDetails user = this.userService.generateUser(new UsernamePasswordAuthenticationToken(data.login(), data.password()));
        var token = tokenService.encode(user);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new UserLogin(hash, token));
    }


    @Operation(summary = "faz o seu registro na base de dados")
    @PostMapping("/register")
    public ResponseEntity<Response> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informaçoẽs para registro") @RequestBody @Valid AuthenticationDTO data) {

       User user = UserDTO.builder()
                .login(data.login())
                .hash(utils.randomHash(50))
                .password(new BCryptPasswordEncoder().encode(data.password()))
                .role(Role.USER)
                .status(StatusUser.P)
                .build()
                .toModel();

        this.userService.save(user);
        this.emailService.sendEmailVerification(user.getLogin(), user.getHash());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new Response("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }

    @Operation(summary = "envia um email com a nova senha")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/alter-password/{email}/{hash}")
    public ResponseEntity<Response> alterPass(@Schema(example = "exemploUsuario@example.com", description = "email do usuário") @PathVariable("email") @Email @NonNullOrBlank String email,
                                              @Schema(example = "HvxZtx6R6JPntroYQ1dgo4281hgwB3p7zoM1yzX3mfyWHdVK3G", description = "Hash do usuário") @PathVariable("hash") @NonNullOrBlank String hash) {

        this.emailService.sendEmailResetPass(email, hash);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Enviamos um email para sua conta, indicando o passo a passo para fazer a troca de senha."));
    }
}