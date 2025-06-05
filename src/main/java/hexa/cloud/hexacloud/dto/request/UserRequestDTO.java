package   hexa.cloud.hexacloud.dto.request;

public class UserRequestDTO implements java.io.Serializable {
    
    private String id;
    private String password;
    private String email;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}       