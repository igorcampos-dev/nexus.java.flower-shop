package com.flowershop.back.services;

public interface ReadersService {
    String fileHtmlConfirmacao(String html);
    String fileSendEmail(String html);
}
