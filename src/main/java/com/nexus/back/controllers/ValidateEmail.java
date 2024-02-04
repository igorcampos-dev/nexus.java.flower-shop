package com.nexus.back.controllers;

import com.nexus.back.services.UserService;
import com.nexus.utils.Readers;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/shop")
public class ValidateEmail {

    private final UserService userService;
    private final Readers readersService;

    @Hidden
    @SneakyThrows
    @GetMapping("/confirme-email")
    public ResponseEntity<String> confirmeEmail(@RequestParam(name = "hash", required = false, defaultValue = "null") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        userService.updateStatus(hash);
        return new ResponseEntity<>(readersService.fileHtml("EmailSucesso"), headers, HttpStatus.OK);
    }

}