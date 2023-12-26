package com.flowershop.back.controllers;

import com.flowershop.back.configuration.UtilsProject;
import com.flowershop.back.configuration.annotations.IsValid;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.LoginResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.TokenService;
import com.flowershop.back.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação do usuário", description = "(Rotas para o usuário fazer autenticação de login, registro, etc)")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenServiceImpl;
    private final EmailService emailService;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenServiceImpl, EmailService emailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceImpl = tokenServiceImpl;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Operation(summary = "efetua o login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informações para login") @RequestBody @Valid AuthenticationDTO data) {
        String hash = this.userService.validateUser(data);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        var token = tokenServiceImpl.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDTO(token, hash));
    }

    @Operation(summary = "faz o seu registro na base de dados")
    @PostMapping("/register")
    public ResponseEntity<ReturnResponseBody> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informaçoẽs para registro") @RequestBody @Valid AuthenticationDTO data) {
        String hash = UtilsProject.randomHash(50);
        String pass = new BCryptPasswordEncoder().encode(data.password());
        User user = this.userService.createUser(data, hash, pass);
        this.userService.save(user);
        this.emailService.sendEmailVerification(data.login(), hash);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Usuário cadastrado com sucesso, mas verifique a caixa de entrada do seu email para validar a sua conta!"));
    }

    @Operation(summary = "envia um email com a nova senha")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/alter-password/{email}/{hash}")
    public ResponseEntity<ReturnResponseBody> alterPass(@Schema(example = "exemploUsuario@example.com", description = "email do usuário") @PathVariable("email") @Email @IsValid String email,
                                                        @Schema(example = "HvxZtx6R6JPntroYQ1dgo4281hgwB3p7zoM1yzX3mfyWHdVK3G", description = "Hash do usuário") @PathVariable("hash") @IsValid String hash) {
        this.emailService.sendEmailResetPass(email, hash);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Enviamos um email para sua conta, indicando o passo a passo para fazer a troca de senha."));
    }
}
