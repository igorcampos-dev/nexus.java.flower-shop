package com.flowershop.back.controllers;

import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flowershop.back.configuration.Utils.fileHtml;

@RestController
@RequestMapping
public class ValidateEmail {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @SneakyThrows
    @GetMapping("/confirme-email")
    public ResponseEntity<String> confirmeEmail(@RequestParam(name = "hash", required = false, defaultValue = "null") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        User user = userRepository.findByHash(hash);

        if (user == null || !userService.updateStatus(hash)) {
            return new ResponseEntity<>(fileHtml("emailFalha"), headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fileHtml("emailSucesso"), headers, HttpStatus.OK);
    }

}
