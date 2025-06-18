package it.uniroma3.siw.authentication;


import javax.sql.DataSource;

import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.model.Credentials.*;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean

    protected SecurityFilterChain configure(final HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
                .authorizeHttpRequests(requests -> requests
                        // .requestMatchers("/**").permitAll()
                        // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                        .requestMatchers(HttpMethod.GET, "/listings/**", "/index", "/index", "/register/**","/login" ,"/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
                        // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register
                        .requestMatchers(HttpMethod.POST, "/register/**", "/login").permitAll()//TODO MODIFICARE
                        .requestMatchers(HttpMethod.GET, "/selectRole").hasAnyAuthority(NO_ROLE)
                        .requestMatchers(HttpMethod.POST, "/selectRole").hasAnyAuthority(NO_ROLE) //SOLO GLI UTENTI CHE NON HANNO ANCORA UN RUOLO POSSONO SCEGLIERNE UNO
                        .requestMatchers(HttpMethod.GET, "/teacher/**").hasAnyAuthority(TEACHER_ROLE)
                        .requestMatchers(HttpMethod.POST, "/teacher/**").hasAnyAuthority(TEACHER_ROLE)
                        .requestMatchers(HttpMethod.GET, "/student/**").hasAnyAuthority(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.POST, "/student/**").hasAnyAuthority(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                        // tutti gli utenti autenticati possono accere alle pagine rimanenti
                        .anyRequest().authenticated()).formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String email = authentication.getName();
                            var credentialsOpt = credentialsRepository.findByUsername(email);

                            if (credentialsOpt.isPresent() && !credentialsOpt.get().isRegistrationComplete()) {
                                response.sendRedirect("/selectRole");
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                )
                .logout(logout -> logout
                        // il logout è attivato con una richiesta GET a "/logout"
                        .logoutUrl("/logout")
                        // in caso di successo, si viene reindirizzati alla home
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll());
        return httpSecurity.build();
    }
}


