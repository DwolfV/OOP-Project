package nl.tudelft.oopp.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class Authentication extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    // add two demo users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManagerConfigurer manager = auth.jdbcAuthentication()
                .dataSource(dataSource).usersByUsernameQuery("select username, password, enabled from users where binary username = ?");
    }


    // Secure the endpoints with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/building/**", "/equipment/**", "/holidays/**",
                        "/openTimes/**", "/restaurant/**", "/rooms/**",
                        "/room_reservations/**", "/supply/**", "/login", "/room_reservations_times/**", "/occasion/**", "/event/**", "/supply_reservations/**", "/friend/**")
                        .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/room_reservations/**", "/event/**", "/supply_reservations/**", "/friend/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/room_reservations/**", "/event/**", "/supply_reservations/**", "/friend/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/room_reservations/**", "/event/**", "/supply_reservations/**", "/friend/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    /**
     * Create a new Bean for JDBC user detail management.
     * It is used for creating users and checking if users exist.
     *
     * @return A new JdbcUserDetailsManager
     * */
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
