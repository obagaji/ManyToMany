package com.jtc.ManyToMany.configClass;

import com.jtc.ManyToMany.dto.TokenDto;
import com.jtc.ManyToMany.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenGenerator {

    @Autowired
    private JwtEncoder jwtEncode;

    @Autowired
    @Qualifier("jwtEncoderRefreshToken")
    private JwtEncoder jwtRefreshEncode;

    public String createAccessToken(Authentication authentication)
    {
        Instant instant = Instant.now();

        Users users = (Users) authentication.getPrincipal();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .issuer("ManyToMany")
                .issuedAt(instant)
                .expiresAt(instant.plus(12, ChronoUnit.HOURS))
                .subject(users.getUsername())
                .build();

        return jwtEncode.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }

    public String createRefreshToken(Authentication authentication)
    {

         Users users = (Users)authentication.getPrincipal();

         Instant instant = Instant.now();

         JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                 .builder()
                 .issuer("ManyToMany")
                 .issuedAt(instant)
                 .expiresAt(instant.plus(6, ChronoUnit.HOURS))
                 .subject(users.getUsername())
                 .build();

         return jwtRefreshEncode.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    public TokenDto createToken(Authentication authentication)
    {

        if (!(authentication.getPrincipal() instanceof Users ))
        {
            throw new BadCredentialsException("not the required credential");
        }

        TokenDto tokenDto = new TokenDto();

        tokenDto.setAccessToken(createAccessToken(authentication));

        tokenDto.setUserIdDto(((Users) authentication.getPrincipal()).getUsername());

        String refreshToken;

        if (authentication.getPrincipal() instanceof Jwt jwt)
        {
            Instant thisTime = Instant.now();

            Instant instant = jwt.getExpiresAt();

            Duration duration = Duration.between(thisTime,instant);

            long durationToDay = duration.toDays();

            if (durationToDay < 7)
            {
                refreshToken = createRefreshToken(authentication);
            }

            else {
                refreshToken = jwt.getTokenValue();
            }
        }

        else {
             refreshToken=createRefreshToken(authentication);
        }

        tokenDto.setRefreshToken(refreshToken);

        return tokenDto;
    }

}
