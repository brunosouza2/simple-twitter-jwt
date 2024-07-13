package br.com.brunosouza.springsecurity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Twitter Simples",
				version = "1",
				description = """
							Projeto simples de twitter para implementar autenticação JWT utilizando 
							Spring Security 6+ e Oauth2.
							
							Ao iniciar a aplicação é criado um usuário admin com as credênciais: 
							
							{
							"username" : "admin",
							"password" : "123" 
							}
							
							Você pode utilizar estas credências para obter acesso aos endpoints protegidos
							inserindo elas no endpoint de login.
							
							O retorno da requisição será um bearer token na variável acessToken que pode
							ser inserido no botão "Authorize".
						
							
						"""
		)

)
@SecurityScheme(
		name = "spring-security-simple-twitter",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

}
