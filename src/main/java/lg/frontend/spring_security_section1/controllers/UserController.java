package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.request.CreateUserDTO;
import lg.frontend.spring_security_section1.DTOs.request.DepositAmount;
import lg.frontend.spring_security_section1.DTOs.response.UserListResponse;
import lg.frontend.spring_security_section1.entities.User;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public CustomResponse<List<UserListResponse>> getAllUsers(@RequestParam(required = false) String name , @RequestParam(required = false) String email) {
        return userService.getAllUsers(name , email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody CreateUserDTO createUserDTO) {
       return userService.createUser(createUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id , user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/deposit/{id}")
    public CustomResponse<User> depositUser(@PathVariable Long id, @RequestBody DepositAmount depositAmount ) {
        return userService.depositAmount(id , depositAmount);
    }

    @PutMapping("/avatar/{id}")
    public CustomResponse<Boolean> updateAvatar(@PathVariable  Long id , @RequestParam("file") MultipartFile file ) {
        return userService.updateAvatar(id , file);
    }
}
