package hexa.cloud.hexacloud.dto.request;

public class VpsAccessRequestDTO {
    private Long userId;
    private Integer vpsId;

    public VpsAccessRequestDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getVpsId() { return vpsId; }
    public void setVpsId(Integer vpsId) { this.vpsId = vpsId; }
}