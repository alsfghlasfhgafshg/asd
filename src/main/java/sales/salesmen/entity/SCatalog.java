package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//服务分类
@Entity
public class SCatalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public SCatalog(String name) {
        this.name = name;
    }

    public SCatalog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Column
    private String name;

    public SCatalog() {

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
