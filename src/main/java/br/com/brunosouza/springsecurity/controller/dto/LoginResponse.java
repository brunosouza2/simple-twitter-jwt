package br.com.brunosouza.springsecurity.controller.dto;

public record LoginResponse(String acessToken, Long expires) {
}
