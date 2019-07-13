package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;


//课程分类
@Entity
public class Serving implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String subtitle;

    @Column
    private String pictureuri;

    @Column
    private String summary;

    @Column
    private String price;

    @OneToOne
    private SCatalog2 sCatalog2;

    public SCatalog2 getsCatalog2() {
        return sCatalog2;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setsCatalog2(SCatalog2 sCatalog2) {
        this.sCatalog2 = sCatalog2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public Serving() {

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
