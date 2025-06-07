package hexa.cloud.hexacloud.dto.request;

import java.io.Serializable;

public class VpsRequestDTO implements Serializable {
    private String name;
    private String ipAddress;
    private String status;
    private String osType;
    private Integer ownerId;

    public VpsRequestDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
}
