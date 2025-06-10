package hexa.cloud.hexacloud.controller;

import hexa.cloud.hexacloud.dto.request.LoginRequestDTO;
import hexa.cloud.hexacloud.dto.request.UserRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.UserResponseDTO;
import hexa.cloud.hexacloud.model.User;
import hexa.cloud.hexacloud.model.Role;
import hexa.cloud.hexacloud.model.UserRole;
import hexa.cloud.hexacloud.repository.UserRepository;
import hexa.cloud.hexacloud.repository.RoleRepository;
import hexa.cloud.hexacloud.repository.UserRoleRepository;
import hexa.cloud.hexacloud.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký user, gán role USER mặc định
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setStatus("ACTIVE");
        user = userRepository.save(user);

        // Gán role USER mặc định
        Role userRole = roleRepository.findByName("USER");
        userRoleRepository.save(new UserRole(user, userRole));

        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getStatus());
        response.setRoles(List.of("USER"));
        return response;
    }

    // Đăng nhập, gửi mail thông báo
    @PostMapping("/login")
public UserResponseDTO login(@RequestBody LoginRequestDTO dto) {
    User user = userRepository.findByUsername(dto.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid password");
    }
    List<String> roles = userRoleRepository.findByUserId(user.getId())
        .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());

    String roleMsg = roles.contains("ADMIN") ? "Admin has been logged" : "User has been logged";
    emailService.sendEmail(
        user.getEmail(),
        "Login Notification",
        roleMsg + " at " + OffsetDateTime.now()
    );

    UserResponseDTO response = new UserResponseDTO(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getFullName(),
        user.getStatus()
    );
    response.setRoles(roles);
    return response;
}

    // ADMIN: Xem tất cả user
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            List<String> roles = userRoleRepository.findByUserId(user.getId())
                .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
            UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getStatus());
            response.setRoles(roles);
            return response;
        }).collect(Collectors.toList());
    }

    // ADMIN: Xóa user
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    // ADMIN: Sửa user
    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user = userRepository.save(user);
        List<String> roles = userRoleRepository.findByUserId(user.getId())
            .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getStatus());
        response.setRoles(roles);
        return response;
    }

    // ADMIN & USER: Lấy thông tin user theo id
    @GetMapping("/{userId}")
    public UserResponseDTO getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = userRoleRepository.findByUserId(user.getId())
            .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
        UserResponseDTO response = new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getStatus()
        );
        response.setRoles(roles);
        return response;
    }
}