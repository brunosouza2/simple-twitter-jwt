package br.com.brunosouza.springsecurity.controller.dto;

public record FeedItemDto(String username,
                          Long tweetId,
                          String content) {
}
