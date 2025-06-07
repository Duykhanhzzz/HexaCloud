package hexa.cloud.hexacloud.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VpsAccessId implements Serializable {
    private Integer userId;
    private Integer vpsId;

    public VpsAccessId() {}

    public VpsAccessId(Integer userId, Integer vpsId) {
        this.userId = userId;
        this.vpsId = vpsId;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getVpsId() { return vpsId; }
    public void setVpsId(Integer vpsId) { this.vpsId = vpsId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VpsAccessId)) return false;
        VpsAccessId that = (VpsAccessId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(vpsId, that.vpsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, vpsId);
    }
}