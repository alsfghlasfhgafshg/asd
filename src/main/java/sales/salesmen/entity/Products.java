package sales.salesmen.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class Products implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "产品名为空")
    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "pcatalogId")
    private PCatalog pCatalog;

    @Column
    private  String  scale;

    @Column
    private String startmoney;

    @Column
    private  String invetmentPeriod;

    @Column
    private String performance;

    @Column
    private String startDate;

    protected Products() {}

    public Products(@NotEmpty(message = "产品名为空") String name, String scale, String startmoney, String invetmentPeriod, String performance, String startDate) {
        this.name = name;
        this.scale = scale;
        this.startmoney = startmoney;
        this.invetmentPeriod = invetmentPeriod;
        this.performance = performance;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PCatalog getpCatalog() {
        return pCatalog;
    }

    public void setpCatalog(PCatalog pCatalog) {
        this.pCatalog = pCatalog;
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
