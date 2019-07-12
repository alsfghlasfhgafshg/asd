package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//课程分类
@Entity
public class SCatalog2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private SCatalog sCatalog;

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
