package hexa.cloud.hexacloud.dto.request.response;

public class UserRoleResponseDTO {
     private Long userId;
    private Integer roleId;

    public UserRoleResponseDTO() {}

    public UserRoleResponseDTO(Long userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}
