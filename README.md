# Simple Twitter API

Este projeto é uma API simples que imita o Twitter, com o propósito de implementar a autenticação stateless utilizando JWT e Spring Security 6 da maneira mais simples possível. O projeto também utiliza Docker para subir um banco de dados MySQL e Swagger para documentação. Além disso, foi usado o OAuth2 Resource Server para criar o token de maneira simplificada.

## Requisitos

- Java 17.0.11+
- Maven
- Docker

## Instalação

1. Clone o repositório:

    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd <NOME_DO_DIRETORIO_CLONADO>
    ```

2. Suba o banco de dados MySQL utilizando Docker:

    ```bash
    cd docker
    docker-compose up
    ```

3. Compile e rode o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Documentação da API

A documentação da API está disponível através do Swagger. Para acessá-la, inicie a aplicação e vá para:

http://localhost:8080/swagger-ui/index.html#/


## Autenticação

Esta API utiliza JWT para autenticação stateless. O OAuth2 Resource Server é utilizado para gerar os tokens de autenticação.

## Estrutura do Projeto

- **src/**: Contém o código fonte da aplicação.
- **docker/**: Contém o arquivo `docker-compose.yml` para subir o banco de dados MySQL.

## Tecnologias Utilizadas

- Java 17.0.11+
- Spring Boot
- Spring Security 6
- JWT
- OAuth2 Resource Server
- Docker
- MySQL
- Swagger

## Licença
Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.
