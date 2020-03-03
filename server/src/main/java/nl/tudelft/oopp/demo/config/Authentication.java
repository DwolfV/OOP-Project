package nl.tudelft.oopp.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class Authentication extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    // add two demo users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}password").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
        auth.jdbcAuthentication()
                .dataSource(dataSource);
                //.withDefaultSchema()
//                .withUser(User.withUsername("user")
//                        .password(passwordEncoder().encode("pass"))
//                        .roles("USER"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .authorizeRequests()
//                .httpBasic()
//                .and()
//                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/rooms/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/rooms").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/rooms/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/rooms/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()       // TODO think about csfr? + HTTPS?
                .formLogin().disable();
    }
}
