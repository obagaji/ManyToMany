package com.jtc.ManyToMany.contPackage;

import com.jtc.ManyToMany.configClass.JwtTokenGenerator;
import com.jtc.ManyToMany.configClass.UserDetailClass;
import com.jtc.ManyToMany.configClass.UserDetailClassService;
import com.jtc.ManyToMany.dto.LoginDto;
import com.jtc.ManyToMany.dto.UsersDto;
import com.jtc.ManyToMany.entity.Users;
import com.jtc.ManyToMany.mapperClasses.ManyToManyMapper;
import com.jtc.ManyToMany.reposit.UsersRepository;
import com.jtc.ManyToMany.servicePackage.AuthService;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class UnAuthorize
{


    private final StudentTeacher studentTeacher;
    private final DaoAuthenticationProvider authProvider;
    private final UserDetailClassService userDetailClassService;
    private final JwtTokenGenerator generator;
    private final AuthService authService;
    private final ManyToManyMapper toManyMapper;
    private final UsersRepository usersRepository;


    @PostMapping("/login")
    public ResponseEntity<String> loginToStart(@RequestBody LoginDto loginDto)
    {
        Authentication authentication = authProvider.
                authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getLoginUser(),
                        loginDto.getLoginPassword()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/login").build(authentication);

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);

        headers.setBearerAuth(generator.createAccessToken(authentication));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @PostMapping("/register")
    public ResponseEntity<String>registerUser(@RequestBody UsersDto usersDto)
    {
        Users users = authService.registerUser(toManyMapper.users(usersDto));

        usersRepository.save(users);

        System.out.println(usersRepository.findByUsername(users.getUsername()));

        UserDetailClass userDetailClass = new UserDetailClass(users);

        Authentication authentication = authProvider.authenticate(UsernamePasswordAuthenticationToken.
                authenticated(users,users.getUserPassword(), Collections.emptyList()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/register").build(users);

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(uri);

        headers.setBearerAuth(generator.createAccessToken(authentication));

        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).build();
    }

    @GetMapping("/view")
    public ResponseEntity<String> getView()
    {
        return ResponseEntity.status(HttpStatus.OK).body("my view");
    }


}
