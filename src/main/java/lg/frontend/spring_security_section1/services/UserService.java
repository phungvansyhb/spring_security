package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.DTOs.request.CreateUserDTO;
import lg.frontend.spring_security_section1.DTOs.response.UserListResponse;
import lg.frontend.spring_security_section1.entities.User;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public CustomResponse<List<UserListResponse>> getAllUsers() {
         List<User> users =  userRepository.find();
         List<UserListResponse> userListResponses = users.stream().map(user -> {
             UserListResponse userListResponse = new UserListResponse();
             userListResponse.setId(user.getId());
             userListResponse.setName(user.getName());
             userListResponse.setEmail(user.getEmail());
             userListResponse.setPassword("*****");
             userListResponse.setPhone(user.getPhone());
             userListResponse.setAvatar(user.getAvatar());
             userListResponse.setRole(user.getRole());
             userListResponse.setCreatedDate(user.getCreatedDate());
             userListResponse.setUpdatedDate(user.getUpdatedDate());
             return userListResponse;
         }).toList();
         return new CustomResponse<>(true , "Success" , userListResponses);
    }

    public ResponseEntity<User> getUserById(Long id) {
        User user =  userRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    public ResponseEntity<User> createUser(CreateUserDTO user) {
        User createUser = User.builder().name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .build();
        User newUser = userRepository.save(createUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
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
}
