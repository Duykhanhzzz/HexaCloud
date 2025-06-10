package hexa.cloud.hexacloud.dto.request.response;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String status;
    private List<String> roles;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String email, String fullName, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
    }

    // Getters and setters (không có password)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}