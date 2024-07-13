package br.com.brunosouza.springsecurity.service;

import br.com.brunosouza.springsecurity.controller.dto.CreateUserDto;
import br.com.brunosouza.springsecurity.entities.Role;
import br.com.brunosouza.springsecurity.entities.User;
import br.com.brunosouza.springsecurity.exception.ExistingUserException;
import br.com.brunosouza.springsecurity.repository.RoleRepository;
import br.com.brunosouza.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static br.com.brunosouza.springsecurity.entities.Role.Values.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserDto userDto) {
        userRepository.findByUsername(userDto.username())
                .ifPresentOrElse(
                        (userFound) -> {
                    throw new ExistingUserException("Usuário já existe");
                },
                        () -> {
                            Role role = roleRepository.findByName(BASIC.name());
                            User user = new User();
                            user.setUsername(userDto.username());
                            user.setPassword(passwordEncoder.encode(userDto.password()));
                            user.setRoles(Set.of(role));
                            userRepository.save(user);
                        }
                );
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
