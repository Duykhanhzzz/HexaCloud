package hexa.cloud.hexacloud.dto.request;

public class RoleRequestDTO {
    private String name;
    private String description;

    public RoleRequestDTO() {}

    public RoleRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}