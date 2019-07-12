package sales.salesmen.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class WxUser {

    @Id
    @Column(nullable = false, length = 40, unique = true)
    String wxopenid;


    @OneToOne(cascade = CascadeType.ALL)
    User user;


    public WxUser() {
    }

    public WxUser(String wxopenid, User user) {
        this.wxopenid = wxopenid;
        this.user = user;
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
