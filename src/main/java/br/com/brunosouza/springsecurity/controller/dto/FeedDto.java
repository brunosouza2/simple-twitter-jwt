package br.com.brunosouza.springsecurity.controller.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItemDto,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements
                       ) {
}
