package hexa.cloud.hexacloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hexa.cloud.hexacloud.dto.request.UserRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.UserResponseDTO;
import hexa.cloud.hexacloud.model.User;
import hexa.cloud.hexacloud.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
     // Giả lập, bạn nên inject service thay vì repository trực tiếp
    @PostMapping("/")
public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
    User user = new User();
        user.setUsername(userRequestDTO.getId());
        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        // ...set các trường khác nếu có...

        user = userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
}
   @PutMapping("/{userId}")
   public String updateUser(@PathVariable int userId , @RequestBody UserRequestDTO userRequestDTO) {
       System.out.println("Updating user with ID: " + userId);
       return   "User updated successfully";
   }
    @DeleteMapping("/{userId}")
   public String deleteUser(@PathVariable int userId) {
       System.out.println("Deleting user with ID: " + userId);
       return "User deleted successfully";
   }
   @PatchMapping("/{userId}")
   public String updateUser(@PathVariable int userId, @RequestParam boolean status) {
         System.out.println("Update user with ID: " + userId + ", status: " + status);
         return "User updated successfully";
    }
    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
    
}
