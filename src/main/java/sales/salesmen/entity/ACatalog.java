package sales.salesmen.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class ACatalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "类型名为空")
    @Column(nullable = false)
    private String name;

    public ACatalog(@NotEmpty(message = "类型名为空") String name) {
        this.name = name;
    }

    protected ACatalog() {}

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
