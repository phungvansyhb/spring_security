package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.request.CreateUserDTO;
import lg.frontend.spring_security_section1.DTOs.response.UserListResponse;
import lg.frontend.spring_security_section1.entities.User;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public CustomResponse<List<UserListResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody CreateUserDTO user) {
       return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id , user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
