package com.orakuma.servitus.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@Profile("!noauth")
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .oauth2ResourceServer(
            (oauth2) -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtConverter())));
    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter customJwtConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
        jwt -> {
          var defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
          Collection<GrantedAuthority> authorities = defaultAuthoritiesConverter.convert(jwt);

          Map<String, Object> realmAccess = jwt.getClaim("realm_access");
          if (realmAccess != null && realmAccess.get("roles") instanceof Collection<?> roles) {
            var realmRoles =
                roles.stream()
                    .map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                    .toList();

            return Stream.concat(authorities.stream(), realmRoles.stream())
                .collect(Collectors.toList());
          }
          return authorities;
        });
    return jwtAuthenticationConverter;
  }
}
