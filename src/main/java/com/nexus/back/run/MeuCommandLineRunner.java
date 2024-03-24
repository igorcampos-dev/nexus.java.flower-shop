package com.nexus.back.run;

import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.Role;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.back.repository.UserRepository;
import com.nexus.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MeuCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public void run(String... args) {
        criarUsuarios();
    }

    private void criarUsuarios() {
        criarUsuarioSeNaoExistir("admin@example.com", "senhaAdmin", Role.ADMIN);
        criarUsuarioSeNaoExistir("user@example.com", "senhaUser", Role.USER);
    }

    private void criarUsuarioSeNaoExistir(String login, String senha, Role role) {
        userRepository.findByLogin(login)
                .ifPresentOrElse(
                        user -> {},
                        () -> {
                            User novoUsuario = new User();
                            novoUsuario.setRole(role);
                            novoUsuario.setStatus(StatusUser.A);
                            novoUsuario.setLogin(login);
                            novoUsuario.setHash(utils.randomHash(50));
                            novoUsuario.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(senha));
                            userRepository.save(novoUsuario);
                            logger.info("------------------------------------------------------------------------------------------");
                            logger.info(String.format("Usuário '%s' foi criado e salvo no banco de dados, sua senha é: '%s' .", login, senha));
                            logger.info("-------------------------------------------------------------------------------------------");
                        });}

    private static final Logger logger = LogManager.getLogger(MeuCommandLineRunner.class);
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

}
