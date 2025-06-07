package hexa.cloud.hexacloud.controller;

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

    // Đăng ký user, gán role USER mặc định
    @PostMapping("/")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
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

    // API gán thêm role ADMIN cho user (chỉ admin mới gọi)
    @PostMapping("/grant-admin/{userId}")
    public String grantAdmin(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Role adminRole = roleRepository.findByName("ADMIN");
        userRoleRepository.save(new UserRole(user, adminRole));
        return "Granted ADMIN role to user " + user.getUsername();
    }

    // Lấy user và roles
    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = userRoleRepository.findByUserId(user.getId())
            .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getStatus());
        response.setRoles(roles);
        return response;
    }

    // Cập nhật user (chỉ chính user hoặc admin mới được sửa)
    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        // TODO: Kiểm tra quyền ở đây (user hoặc admin mới được sửa)
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user = userRepository.save(user);
        List<String> roles = userRoleRepository.findByUserId(user.getId())
            .stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList());
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getStatus());
        response.setRoles(roles);
        return response;
    }

    // Xóa user (chỉ admin mới được xóa)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        // TODO: Kiểm tra quyền admin trước khi xóa
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    // Login trả về roles
    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        // So sánh password (nếu đã mã hóa thì dùng passwordEncoder.matches)
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
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

    // Gửi OTP về email
    @PostMapping("/request-otp")
    public String requestOtp(@RequestBody UserRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        // Sinh OTP
        String otp = String.valueOf((int)((Math.random() * 900000) + 100000));
        user.setOtp(otp);
        user.setOtpExpiredAt(OffsetDateTime.now().plusMinutes(5));
        userRepository.save(user);

        // Gửi mail
        emailService.sendEmail(
            user.getEmail(),
            "Your OTP Code",
            "Your OTP code is: " + otp + ". It will expire in 5 minutes."
        );
        return "OTP sent to email";
    }

    // Đăng nhập bằng OTP
    @PostMapping("/login-otp")
    public UserResponseDTO loginWithOtp(@RequestBody UserRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getOtp() == null || !user.getOtp().equals(dto.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }
        if (user.getOtpExpiredAt() == null || user.getOtpExpiredAt().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }
        // Xóa OTP sau khi dùng
        user.setOtp(null);
        user.setOtpExpiredAt(null);
        userRepository.save(user);

        // Lấy roles
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

        // Gửi mail thông báo đăng nhập thành công
        emailService.sendEmail(
            user.getEmail(),
            "Login Notification",
            "You have just logged in to your account at " + OffsetDateTime.now()
        );

        return response;
    }
}