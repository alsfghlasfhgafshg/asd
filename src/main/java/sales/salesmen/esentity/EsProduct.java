package sales.salesmen.esentity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import sales.salesmen.entity.Products;

import javax.persistence.Id;
import java.io.Serializable;


@Document(indexName = "product",type = "product")
public class EsProduct implements Serializable {

    @Id
    private String id;

    @Field(type=FieldType.Long)
    private Long productId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String type;

    @Field(type = FieldType.Text,index = false)
    private String scale;

    @Field(type = FieldType.Text,index = false)
    private String startmoney;

    @Field(type = FieldType.Text,index = false)
    private String invetmentPeriod;

    @Field(type = FieldType.Text,index = false)
    private String performance;

    @Field(type = FieldType.Text,index = false)
    private String startDate;

    protected EsProduct() {}

    public EsProduct(Products products) {
        this.productId = products.getId();
        this.name = products.getName();
        this.type = products.getType();
        this.scale = products.getScale();
        this.startmoney = products.getStartmoney();
        this.invetmentPeriod = products.getInvetmentPeriod();
        this.performance = products.getPerformance();
        this.startDate = products.getStartDate();
    }

    public EsProduct(Long productId, String name, String type, String scale, String startmoney, String invetmentPeriod, String performance, String startDate) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.scale = scale;
        this.startmoney = startmoney;
        this.invetmentPeriod = invetmentPeriod;
        this.performance = performance;
        this.startDate = startDate;
    }

    public void update(Products products) {
        this.productId = products.getId();
        this.name = products.getName();
        this.type = products.getType();
        this.scale = products.getScale();
        this.startmoney = products.getStartmoney();
        this.invetmentPeriod = products.getInvetmentPeriod();
        this.performance = products.getPerformance();
        this.startDate = products.getStartDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStartmoney() {
        return startmoney;
    }

    public void setStartmoney(String startmoney) {
        this.startmoney = startmoney;
    }

    public String getInvetmentPeriod() {
        return invetmentPeriod;
    }

    public void setInvetmentPeriod(String invetmentPeriod) {
        this.invetmentPeriod = invetmentPeriod;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
