package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.DTOs.request.CreateUserDTO;
import lg.frontend.spring_security_section1.DTOs.request.DepositAmount;
import lg.frontend.spring_security_section1.DTOs.request.LoginDTO;
import lg.frontend.spring_security_section1.DTOs.response.LoginResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.UserListResponse;
import lg.frontend.spring_security_section1.entities.*;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.repositories.RolePermissionRepository;
import lg.frontend.spring_security_section1.repositories.RoleRepository;
import lg.frontend.spring_security_section1.repositories.RoleUserRepository;
import lg.frontend.spring_security_section1.repositories.UserRepository;
import lg.frontend.spring_security_section1.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleUserRepository roleUserRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;


    public CustomResponse<List<UserListResponse>> getAllUsers(String name, String email) {
        String searchName = StringUtil.stringToSearchLike(name);
        String searchEmail = StringUtil.stringToSearchLike(email);
        List<User> users = userRepository.findAllByUsernameLikeAndEmailLike(searchName, searchEmail);
        List<UserListResponse> userListResponses = users.stream().map(user -> {
            UserListResponse userListResponse = new UserListResponse();
            userListResponse.setId(user.getId());
            userListResponse.setUsername(user.getUsername());
            userListResponse.setEmail(user.getEmail());
            userListResponse.setPassword("*****");
            userListResponse.setPhone(user.getPhone());
            userListResponse.setAvatar(user.getAvatar());
            userListResponse.setCreatedDate(user.getCreatedDate());
            userListResponse.setUpdatedDate(user.getUpdatedDate());
            return userListResponse;
        }).toList();
        return new CustomResponse<>(true, "Success", userListResponses);
    }

    public ResponseEntity<User> getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(CreateUserDTO user) {
        String hashedPass = passwordEncoder.encode(user.getPassword());
        User createUser = User.builder().username(user.getUsername())
                .email(user.getEmail())
                .password(hashedPass)
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .balance(0L)
                .build();
        User newUser = userRepository.save(createUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            User updatedUser = userRepository.save(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deleteUser(Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public CustomResponse<User> depositAmount(Long id, DepositAmount depositAmount) {
        Long amount = depositAmount.amount();
        if (amount <= 0) {
            return new CustomResponse<>(false, "amount is not valid", null);
        }
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
            return new CustomResponse<>(true, "ok", user);
        } else
            return new CustomResponse<>(false, "user not found", null);
    }

    public CustomResponse<Boolean> updateAvatar(Long id , MultipartFile file) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/" ;
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                boolean isSuccess = directory.mkdirs();
                if(!isSuccess) {
                    return new CustomResponse<>(false, "cannot create upload folder", null);
                }
            }
            try{
                String filePath = uploadDir + file.getOriginalFilename();
                File uploadedFile = new File(filePath);
                file.transferTo(uploadedFile);
                user.setAvatar(filePath);
                userRepository.save(user);
                return new CustomResponse<>(false, "ok", true);
            } catch (IOException e) {
                return new CustomResponse<>(false, "user not found", null);
            }
        }else
            return new CustomResponse<>(false, "user not found", null);
    }

    public LoginResponseDTO login(LoginDTO loginDTO){
        try{
            User user = userRepository.findByUsername(loginDTO.username());
            if(user != null) {
                boolean match = passwordEncoder.matches(loginDTO.password() , user.getPassword());
                if(match) {
                    LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                    List<Role> roles = roleUserRepository.findByUserId(user.getId()).stream().map(RoleUser::getRole).toList();
                    Set<Permission> permissions = new HashSet<>();
                    roles.forEach(role -> {
                        List<Permission> tmpPermissions = rolePermissionRepository.findAllByRoleId(role.getId()).stream().map(RolePermission::getPermission).toList();
                        permissions.addAll(tmpPermissions);
                    });
                    loginResponseDTO.setUsername(user.getUsername());
                    loginResponseDTO.setAvatar(user.getAvatar());
                    loginResponseDTO.setEmail(user.getEmail());
                    loginResponseDTO.setRoleList(roles);
                    loginResponseDTO.setPhone(user.getPhone());
                    loginResponseDTO.setPermissionList(permissions.stream().toList());
                    loginResponseDTO.setBalance(user.getBalance());
                    return loginResponseDTO;
                }
            }
            return null;

        }catch (Exception e){
            return null;
        }
    }

}
