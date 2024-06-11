package com.jtc.ManyToMany.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Setter
@Getter
public class LoginDto {

    private String loginPassword;

    private String loginUser;

}
