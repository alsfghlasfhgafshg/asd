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

    public SCatalog2() {

    }

    public SCatalog2(Integer id, String name, SCatalog sCatalog) {
        this.id = id;
        this.name = name;
        this.sCatalog = sCatalog;
    }

    public SCatalog2(String name, SCatalog sCatalog) {
        this.name = name;
        this.sCatalog = sCatalog;
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
