package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


//课程分类
@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String pictureuri;

    @Column
    private String teacher;

    @Column
    private String summary;

    @Column
    private int sumplay;

    @Column
    private int sumstar;

    @Column
    private String srcuri;

    @OneToOne
    private CCatalog cCatalog;

    @OneToOne
    private CCatalog2 cCatalog2;

    @ManyToMany
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Course() {

    }

    public Course(Long courseid, String title, String pictureuri, String teacher,
                  String summary, int sumplay, int sumstar,
                  String srcuri, CCatalog cCatalog, CCatalog2 cCatalog2) {
        this.id = courseid;
        this.title = title;
        this.pictureuri = pictureuri;
        this.teacher = teacher;
        this.summary = summary;
        this.sumplay = sumplay;
        this.sumstar = sumstar;
        this.srcuri = srcuri;
        this.cCatalog = cCatalog;
        this.cCatalog2 = cCatalog2;
    }

    public String getSrcuri() {
        return srcuri;
    }

    public void setSrcuri(String srcuri) {
        this.srcuri = srcuri;
    }

    public CCatalog getcCatalog() {
        return cCatalog;
    }

    public void setcCatalog(CCatalog cCatalog) {
        this.cCatalog = cCatalog;
    }

    public CCatalog2 getcCatalog2() {
        return cCatalog2;
    }

    public void setcCatalog2(CCatalog2 cCatalog2) {
        this.cCatalog2 = cCatalog2;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSumplay() {
        return sumplay;
    }

    public void setSumplay(int sumplay) {
        this.sumplay = sumplay;
    }

    public int getSumstar() {
        return sumstar;
    }

    public void setSumstar(int sumstar) {
        this.sumstar = sumstar;
    }
}
