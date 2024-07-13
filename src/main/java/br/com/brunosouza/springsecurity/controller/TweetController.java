package br.com.brunosouza.springsecurity.controller;

import br.com.brunosouza.springsecurity.controller.dto.CreateTweetDto;
import br.com.brunosouza.springsecurity.controller.dto.FeedDto;
import br.com.brunosouza.springsecurity.service.TweetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/feed")
    @Operation(
            description = "Lista todos os tweets de maneira paginada"
    )
    @ApiResponse(responseCode = "200")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var tweets = tweetService.feed(page, pageSize);
        return ResponseEntity.ok(new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }

    @PostMapping
    @Operation(
            description = "Cria um tweet para o usuário com token.",
            security = @SecurityRequirement(name = "spring-security-simple-twitter"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "O recurso foi criado com sucesso.", content = {
                            @Content
                    }),
                    @ApiResponse(responseCode = "401", description = "Token de usuário inválido.", content = {
                            @Content
                    }),
            }
    )
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto tweetDto, JwtAuthenticationToken token) {
        long tweetId = tweetService.createTweet(tweetDto.content(), token).getTweetId();

            var location = UriComponentsBuilder
                    .fromPath("http://localhost:8080/tweets/{tweetId}")
                    .buildAndExpand(tweetId)
                    .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{tweetId}")
    @Operation(
            description = "Deleta um tweet do usuário com token.",
            security = @SecurityRequirement(name = "spring-security-simple-twitter"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "O tweet foi deletado com sucesso.", content = {
                            @Content
                    }),
                    @ApiResponse(responseCode = "422", description = "Não foi possível processar a solicitação pois " +
                            "o tweet não existe ou não pertence ao usuário.", content = {
                            @Content
                    })
            }
    )
    public ResponseEntity<Void> deleteTweet(@PathVariable Long tweetId, JwtAuthenticationToken token) {
        tweetService.deleteTweet(tweetId, token);
        return ResponseEntity.noContent().build();
    }
}
