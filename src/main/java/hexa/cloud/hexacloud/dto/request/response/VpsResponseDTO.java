package hexa.cloud.hexacloud.dto.request.response;

public class VpsResponseDTO {
    private Long id;
    private String name;    
    private String ipAddress;
    private String status;
    private String osType;
    private String createdAt;
    private String updatedAt;
    private UserResponseDTO user;
    
    public VpsResponseDTO() {
    }
    public VpsResponseDTO(Long id, String name, String ipAddress, String status, String osType, String createdAt, String updatedAt, UserResponseDTO user) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.status = status;
        this.osType = osType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOsType() {
        return osType;
    }
    public void setOsType(String osType) {
        this.osType = osType;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public UserResponseDTO getUser() {
        return user;
    }
    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
