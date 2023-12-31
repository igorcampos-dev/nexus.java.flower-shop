package com.flowershop.back.run;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import static com.flowershop.back.configuration.UtilsProject.randomHash;

@Component
public class MeuCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository usuarioService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
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
                            System.out.println("------------------------------------------------------------------------------------------");
                            System.out.println("Usuário: " + login + " foi criado e salvo no banco de dados, sua senha é: " + senha + ".");
                            System.out.println("-------------------------------------------------------------------------------------------");

                        }
                );
    }
}
