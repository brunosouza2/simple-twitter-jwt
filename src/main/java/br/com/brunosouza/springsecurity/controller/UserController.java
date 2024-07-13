package br.com.brunosouza.springsecurity.controller;

import br.com.brunosouza.springsecurity.controller.dto.CreateUserDto;
import br.com.brunosouza.springsecurity.entities.User;
import br.com.brunosouza.springsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            description = "Cria um usuário"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "A requisição foi bem sucedida!", content = {
                            @Content
                    }),
                    @ApiResponse(responseCode = "409", description = "O usuário já existe, portanto não foi possível concluir a solicitação", content = {
                            @Content
                    })
            }
    )
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            description = "Lista todos os usuários",
            security = @SecurityRequirement(name = "spring-security-simple-twitter", scopes = "admin")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "A requisição foi bem sucedida!"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autorizado.", content = {
                            @Content
                    }),
            }
    )
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}

