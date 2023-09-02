package br.ueg.prog.webi.faculdade.configuration;

import br.ueg.prog.webi.api.config.ApiSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends ApiSecurityConfig {
    @Override
    protected void configureHttpSecurity(HttpSecurity http)  throws Exception{
    }

    @Override
    protected List<String> getCustomFreeAccessPaterns() {
        //TODO alterção moduleAdmin
        return Arrays.asList("/api/v1/usuarios/inicializar/**");
    }
}
