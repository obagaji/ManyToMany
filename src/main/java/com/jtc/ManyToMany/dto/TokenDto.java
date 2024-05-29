package com.jtc.ManyToMany.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor

public class TokenDto {

    private String userIdDto;
    private String accessToken;
    private String refreshToken;

    public String getUserIdDto() {
        return userIdDto;
    }

    public void setUserIdDto(String userIdDto) {
        this.userIdDto = userIdDto;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
