package lg.frontend.spring_security_section1.configs;

import lg.frontend.spring_security_section1.repositories.UserRepository;
import lg.frontend.spring_security_section1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
//                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/users").authenticated()
                                .requestMatchers("/api/login/**").permitAll()
                                .requestMatchers("/welcome/**").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())   // Cấu hình này cho phép sử dụng HTTP Basic Authentication. Khi người dùng gửi yêu cầu, họ sẽ cần cung cấp thông tin xác thực (username và password) trong header của yêu cầu qua trường Authenication
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        // không tạo session mà sẽ sử dụng token
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    /*SS sử dụng userDetailService để xử lý xác thực người dùng, nếu hợp lệ ss sẽ tạo 1 đối tượng Authenication cho người dùng đó chứa các thông tin của
    họ (hay còn gọi là SecurityContextHolder) */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("user name :" + username);
            lg.frontend.spring_security_section1.entities.User user = userRepository.findByUsername(username);
            if (user != null) {
                return User.withUsername(user.getUsername())
                        .password(user.getPassword()) // trả về password stored in database and SS auto compare with password in request header
                        .roles(user.getRole())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
