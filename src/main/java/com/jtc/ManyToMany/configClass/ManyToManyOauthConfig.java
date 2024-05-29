package com.jtc.ManyToMany.configClass;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor
public class ManyToManyOauthConfig {


   private final KeyUtil keyUtil;
   private final JwtUserDetailConverter jwtUserDetailConverter;
   private final JwtFilter jwtFilter;



    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return  httpSecurity
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api").permitAll()
                        .requestMatchers("/api/**").authenticated())
                .formLogin(Customizer.withDefaults())
                .oauth2ResourceServer(auth -> auth.jwt(jwt -> jwt.
                        jwtAuthenticationConverter( new JwtUserDetailConverter())))
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((except)-> except.authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public JwtDecoder jwtDecoderAccessToken()
    {
        return NimbusJwtDecoder.withPublicKey(
                keyUtil.getAccessPublicKey()).build();
    }
    @Bean
    @Primary
    public JwtEncoder jwtEncoderAccessToken()
    {
        JWK jwk = new RSAKey.Builder(keyUtil.getAccessPublicKey())
                .privateKey(keyUtil.getAccessPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return  new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    @Qualifier
    public JwtDecoder jwtDecoderRefreshToken()
    {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessRefreshPublicKey()).build();
    }

    @Bean
    @Qualifier("jwtEncoderRefreshToken")
    public JwtEncoder jwtEncoderRefreshToken()
    {
        JWK jwk = new RSAKey.Builder(keyUtil.getAccessRefreshPublicKey())
                .privateKey(keyUtil.getAccessRefreshPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    @Qualifier
    public JwtAuthenticationProvider jwtAuthenticationProvider()
    {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtDecoderRefreshToken());
        provider.setJwtAuthenticationConverter(jwtUserDetailConverter);
        return provider;
    }

}

