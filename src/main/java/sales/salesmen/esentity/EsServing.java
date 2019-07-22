package sales.salesmen.esentity;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import sales.salesmen.entity.Serving;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "serving",type = "serving")
public class EsServing implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long servingId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String subtitle;

    @Field(type = FieldType.Text,index = false)
    private String pictureuri;

    @Field(type = FieldType.Text)
    private String summary;

    @Field(type = FieldType.Text,index = false)
    private String price;

    protected EsServing() {
    }

    public EsServing(Serving serving) {
        this.servingId = serving.getId();
        this.title = serving.getTitle();
        this.subtitle = serving.getSubtitle();
        this.pictureuri = serving.getPictureuri();
        this.summary = serving.getSummary();
        this.price = serving.getPrice();
    }

    public EsServing(Long servingId, String title, String subtitle, String pictureuri, String summary, String price) {
        this.servingId = servingId;
        this.title = title;
        this.subtitle = subtitle;
        this.pictureuri = pictureuri;
        this.summary = summary;
        this.price = price;
    }

    public void update(Serving serving) {
        this.servingId = serving.getId();
        this.title = serving.getTitle();
        this.subtitle = serving.getSubtitle();
        this.pictureuri = serving.getPictureuri();
        this.summary = serving.getSummary();
        this.price = serving.getPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
