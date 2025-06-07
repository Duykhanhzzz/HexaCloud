package hexa.cloud.hexacloud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vps_access")
public class VpsAccess {
    @EmbeddedId
    private VpsAccessId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("vpsId")
    @JoinColumn(name = "vps_id")
    private Vps vps;

    public VpsAccess() {}

    public VpsAccess(User user, Vps vps) {
        this.user = user;
        this.vps = vps;
        this.id = new VpsAccessId(user.getId().intValue(), vps.getId());
    }

    public VpsAccessId getId() { return id; }
    public void setId(VpsAccessId id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Vps getVps() { return vps; }
    public void setVps(Vps vps) { this.vps = vps; }
}