package br.com.brunosouza.springsecurity.service;

import br.com.brunosouza.springsecurity.controller.dto.FeedItemDto;
import br.com.brunosouza.springsecurity.entities.Tweet;
import br.com.brunosouza.springsecurity.entities.User;
import br.com.brunosouza.springsecurity.repository.TweetRepository;
import br.com.brunosouza.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Tweet createTweet(String content, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidBearerTokenException("Token de usuário inválido"));

        Tweet newTweet = new Tweet();
        newTweet.setContent(content);
        newTweet.setUser(user);

        return tweetRepository.save(newTweet);
    }

    @Transactional
    public void deleteTweet(Long tweetId, JwtAuthenticationToken token) {
        Long tweetIdForDelete = tweetRepository.findByUserUserID(UUID.fromString(token.getName())).stream()
                .map(Tweet::getTweetId)
                .filter(twId -> twId.equals(tweetId))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        tweetRepository.deleteById(tweetIdForDelete);
    }

    public Page<FeedItemDto> feed(int page, int pagesize) {
         return tweetRepository.findAll(PageRequest.of(page, pagesize, DESC,
                 "creationTimestamp"))
                 .map(tweet -> new FeedItemDto(
                         tweet.getUser().getUsername(),
                         tweet.getTweetId(),
                         tweet.getContent()
                 ));
    }
}
