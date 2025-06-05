package hexa.cloud.hexacloud.dto.request;

public class VpsRequestDTO implements java.io.Serializable {

    private String vpsId;
    private String vpsName;
    private String vpsDescription;
    private String vpsImage;
    private String vpsFlavor;
    private String vpsNetwork;

    public VpsRequestDTO() {
    }

    public VpsRequestDTO(String vpsId, String vpsName, String vpsDescription, String vpsImage, String vpsFlavor, String vpsNetwork) {
        this.vpsId = vpsId;
        this.vpsName = vpsName;
        this.vpsDescription = vpsDescription;
        this.vpsImage = vpsImage;
        this.vpsFlavor = vpsFlavor;
        this.vpsNetwork = vpsNetwork;
    }

    public String getVpsId() {
        return vpsId;
    }

    public void setVpsId(String vpsId) {
        this.vpsId = vpsId;
    }

    public String getVpsName() {
        return vpsName;
    }

    public void setVpsName(String vpsName) {
        this.vpsName = vpsName;
    }

    public String getVpsDescription() {
        return vpsDescription;
    }

    public void setVpsDescription(String vpsDescription) {
        this.vpsDescription = vpsDescription;
    }

    public String getVpsImage() {
        return vpsImage;
    }

    public void setVpsImage(String vpsImage) {
        this.vpsImage = vpsImage;
    }

    public String getVpsFlavor() {
        return vpsFlavor;
    }

    public void setVpsFlavor(String vpsFlavor) {
        this.vpsFlavor = vpsFlavor;
    }

    public String getVpsNetwork() {
        return vpsNetwork;
    }

    public void setVpsNetwork(String vpsNetwork) {
        this.vpsNetwork = vpsNetwork;
    }
    
}
