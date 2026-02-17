package cloudflight.integra.backend.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors((cors) -> cors.configurationSource(corsConfigurationSource));

        http.sessionManagement((session)
            -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling((exception)
            -> exception.authenticationEntryPoint(
                (req, resp, ex)
                    -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())));

        http.authorizeHttpRequests((authorize)
            -> authorize.anyRequest().permitAll());

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
