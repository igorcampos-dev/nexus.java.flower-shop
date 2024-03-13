package com.nexus.back.services.impl.email;

import com.nexus.back.domain.dto.flower.MessageDTO;
import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.domain.entity.User;
import com.nexus.back.repositories.operations.FlowerDatabaseOperations;
import com.nexus.back.repositories.operations.UserDatabaseOperations;
import com.nexus.back.services.EmailService;
import com.nexus.mail.properties.EmailProperties;
import com.nexus.mail.service.email.SendEmailService;
import com.nexus.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import static com.nexus.utils.Utils.replaceUrlEncodedSpaces;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final FlowerDatabaseOperations flowerDatabaseOperations;
    private final UserDatabaseOperations userDatabaseOperations;
    private final Utils utils;
    private final SendEmailService emailService;
    private final EmailMethodsSupport emailMethodsSupport;
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @SneakyThrows
    @Override
    public void sendEmailVerification(String email, String hash) {
        EmailProperties emailProperties = emailMethodsSupport.getMessageConfirmation(hash, email);
        emailService.send(emailProperties);
    }

    @SneakyThrows
    @Override
    public void sendEmailUser(MessageDTO message) {
        userDatabaseOperations.userExistsByHash(message.hash());
        ResponseFlowerGet flower = flowerDatabaseOperations.findByFilename(replaceUrlEncodedSpaces(message.flower()));
        Resource image = emailMethodsSupport.createImage(flower);
        EmailProperties emailProperties = emailMethodsSupport.getMessageFlower(flower, message, image);
        emailService.send(emailProperties);
    }

    @SneakyThrows
    @Override
    public void sendEmailResetPass(String email, String hash) {
        String newPass = utils.randomHash(12);
        User user = userDatabaseOperations.findByLoginAndHash(email, hash);
        user.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(newPass));
        userDatabaseOperations.save(user);

        EmailProperties emailProperties = emailMethodsSupport.getMessageRecoveryPass(newPass, email);
        emailService.send(emailProperties);
    }

}
