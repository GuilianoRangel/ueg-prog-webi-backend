package br.ueg.prog.webi.faculdade.configuration;

import br.ueg.prog.webi.api.config.ApiSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends ApiSecurityConfig {
    @Override
    protected void configureHttpSecurity(HttpSecurity http) {

    }
}
