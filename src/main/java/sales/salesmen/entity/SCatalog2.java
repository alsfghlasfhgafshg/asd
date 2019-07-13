package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//服务分类2
@Entity
public class SCatalog2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    SCatalog sCatalog;

    public SCatalog getsCatalog() {
        return sCatalog;
    }

    public void setsCatalog(SCatalog sCatalog) {
        this.sCatalog = sCatalog;
    }

    protected SCatalog2() {

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
