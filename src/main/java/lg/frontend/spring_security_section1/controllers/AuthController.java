package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.request.LoginDTO;
import lg.frontend.spring_security_section1.DTOs.response.LoginResponseDTO;
import lg.frontend.spring_security_section1.entities.Permission;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.services.UserService;
import lg.frontend.spring_security_section1.utils.JWTToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTToken jwtTokenUtil;
    private final UserService userService;

    public AuthController(JWTToken jwtTokenUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public CustomResponse<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
        LoginResponseDTO loginResponseDTO = userService.login(loginDTO);
        String token = jwtTokenUtil.generateToken(loginResponseDTO.getUsername() , loginResponseDTO.getPermissionList().stream().map(Permission::getPermissionCode).toList());
        loginResponseDTO.setToken(token);
        return new CustomResponse<>(true,"ok", loginResponseDTO);
    }
}
