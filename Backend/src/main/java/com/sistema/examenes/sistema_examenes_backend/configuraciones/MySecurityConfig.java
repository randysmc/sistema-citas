package com.sistema.examenes.sistema_examenes_backend.configuraciones;


import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UserDetailsServiceImplementacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsServiceImplementacion userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/v3/api-docs/**",          // Ruta para la documentación de OpenAPI
                        "/swagger-ui/**",           // Ruta para Swagger UI
                        "/swagger-resources/**",    // Ruta para los recursos de Swagger
                        "/generate-token",
                        "/usuarios/**",
                        "/validate-token",
                        "/empleados/**",
                        "/recursos/**",
                        "/servicios/**",
                        "/roles/**",
                        "/horarios-laborales/**",
                        "/dias-festivos/**",
                        "/citas/**",
                        "/reservas/**",
                        "/comprobantes/**",
                        "/facturas/**",
                        "/reportes/**",
                        "/uploads/**",
                        "/api/**",
                        "/negocios/**").permitAll() // Permitir acceso sin autenticación
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated() // Otras rutas requieren autenticación
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}