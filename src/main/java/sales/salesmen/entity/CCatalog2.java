package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//课程分类2
@Entity
public class CCatalog2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    protected CCatalog2() {

    }

    public CCatalog2(String name) {
        this.name = name;
    }

    public CCatalog2(Integer id, String name) {
        this.id = id;
        this.name = name;
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
