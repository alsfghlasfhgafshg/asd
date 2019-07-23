package sales.salesmen.entity;

import org.springframework.beans.factory.annotation.Autowired;
import sales.salesmen.repository.AuthorityRepository;

import javax.persistence.*;
import java.io.Serializable;


//课程分类
@Entity
public class CCatalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.DETACH)
    private Authority authority;


    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }


    public CCatalog(String name) {
        this.name = name;
    }

    public CCatalog(Integer id, String name, Authority authority) {
        this.id = id;
        this.name = name;
        this.authority = authority;
    }


    public CCatalog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    protected CCatalog() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
