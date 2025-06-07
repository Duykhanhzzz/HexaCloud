package hexa.cloud.hexacloud.dto.request.response;

public class VpsAccessResponseDTO {
    private Long userId;
    private Integer vpsId;

    public VpsAccessResponseDTO() {}

    public VpsAccessResponseDTO(Long userId, Integer vpsId) {
        this.userId = userId;
        this.vpsId = vpsId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getVpsId() { return vpsId; }
    public void setVpsId(Integer vpsId) { this.vpsId = vpsId; }
}