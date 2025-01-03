package lg.frontend.spring_security_section1.configs;

import lg.frontend.spring_security_section1.entities.Permission;
import lg.frontend.spring_security_section1.entities.Role;
import lg.frontend.spring_security_section1.entities.RolePermission;
import lg.frontend.spring_security_section1.entities.RoleUser;
import lg.frontend.spring_security_section1.exceptions.SecurityAccessDeniedHandler;
import lg.frontend.spring_security_section1.repositories.RolePermissionRepository;
import lg.frontend.spring_security_section1.repositories.RoleUserRepository;
import lg.frontend.spring_security_section1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleUserRepository roleUserRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    SecurityAccessDeniedHandler securityAccessDeniedHandler;

    @Autowired
    @Lazy
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
//                .requiresChannel(channel -> channel.anyRequest().requiresSecure())  // chi nhan request tu https
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/welcome").permitAll()
                                .anyRequest().authenticated())
                //.httpBasic(Customizer.withDefaults())   // Cấu hình này cho phép sử dụng HTTP Basic Authentication. Khi người dùng gửi yêu cầu, họ sẽ cần cung cấp thông tin xác thực (username và password) trong header của yêu cầu qua trường Authenication
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        // không tạo session mà sẽ sử dụng token
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /*SS sử dụng userDetailService để xử lý xác thực người dùng, nếu hợp lệ ss sẽ tạo 1 đối tượng Authenication cho người dùng đó chứa các thông tin của
    họ (hay còn gọi là SecurityContextHolder) */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            lg.frontend.spring_security_section1.entities.User user = userRepository.findByUsername(username);
            if (user != null) {
                return User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .build();
            }
            throw new UsernameNotFoundException("User not found");
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
