package com.flowershop.back.controllers;

import com.flowershop.back.services.ReadersService;
import com.flowershop.back.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ValidateEmail {

    private final UserService userService;
    private final ReadersService readersService;

    public ValidateEmail(UserService userService, ReadersService readersService) {
        this.userService = userService;
        this.readersService = readersService;
    }

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