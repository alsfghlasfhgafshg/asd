package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//课程分类
@Entity
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String pictureuri;

    @Column
    private String summary;

    @Column
    private String price;

    @Column
    private SCatalog2 sCatalog2;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column
    private CCatalog2 cCatalog2;

    public CCatalog2 getcCatalog2() {
        return cCatalog2;
    }

    public void setcCatalog2(CCatalog2 cCatalog2) {
        this.cCatalog2 = cCatalog2;
    }

    protected Service() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureuri() {
        return pictureuri;
    }

    public void setPictureuri(String pictureuri) {
        this.pictureuri = pictureuri;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
