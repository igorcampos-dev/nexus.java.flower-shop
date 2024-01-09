package com.flowershop.back.run;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import static com.flowershop.back.configuration.UtilsProject.randomHash;

@Component
@AllArgsConstructor
public class MeuCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(MeuCommandLineRunner.class);

    private final UserRepository usuarioService;

    private final UserRepository userRepository;

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
                        user -> {
                        },
                        () -> {
                            User novoUsuario = new User();
                            novoUsuario.setRole(role);
                            novoUsuario.setStatus(StatusUser.A);
                            novoUsuario.setLogin(login);
                            novoUsuario.setHash(randomHash(50));
                            novoUsuario.setPassword(new BCryptPasswordEncoder().encode(senha));

                            usuarioService.save(novoUsuario);
                            logger.info("------------------------------------------------------------------------------------------");
                            logger.info(String.format("Usuário %s foi criado e salvo no banco de dados, sua senha é: %s .", login, senha));
                            logger.info("-------------------------------------------------------------------------------------------");

                        }
                );
    }
}
