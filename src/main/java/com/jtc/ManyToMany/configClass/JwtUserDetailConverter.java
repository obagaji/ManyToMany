package com.jtc.ManyToMany.configClass;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtUserDetailConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
            UserDetailClass userDetailClass = new UserDetailClass();
            userDetailClass.setUsername(source.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetailClass,source, Collections.emptyList());
        }


}
