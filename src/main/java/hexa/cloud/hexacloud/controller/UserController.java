package hexa.cloud.hexacloud.controller;

import hexa.cloud.hexacloud.dto.request.LoginRequestDTO;
import hexa.cloud.hexacloud.dto.request.UserRegisterRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.UserResponseDTO;
import hexa.cloud.hexacloud.model.User;
import hexa.cloud.hexacloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRegisterRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        User saved = userService.registerUser(user);
        return toDTO(saved);
    }

    // Login
    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody LoginRequestDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        return toDTO(user);
    }

    // Get all users
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Get user by id
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return toDTO(userService.getUserById(id));
    }

    // Update user
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRegisterRequestDTO dto) {
        User updated = new User();
        updated.setFullName(dto.getFullName());
        updated.setEmail(dto.getEmail());
        updated.setStatus("ACTIVE");
        updated.setRole("USER");
        return toDTO(userService.updateUser(id, updated));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getStatus(),
                user.getRole()
        );
    }
}