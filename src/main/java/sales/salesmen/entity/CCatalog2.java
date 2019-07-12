package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//课程分类
@Entity
public class CCatalog2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private  CCatalog cCatalog;

    protected CCatalog2() {

    }

    public CCatalog getcCatalog() {
        return cCatalog;
    }

    public void setcCatalog(CCatalog cCatalog) {
        this.cCatalog = cCatalog;
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
