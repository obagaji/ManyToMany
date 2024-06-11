package com.jtc.ManyToMany.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsersDto {

    private long   userId;

    private String username;

    private String userPassword;

    private String roles;

}
