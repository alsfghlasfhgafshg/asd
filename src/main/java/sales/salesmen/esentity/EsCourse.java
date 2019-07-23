package sales.salesmen.esentity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import sales.salesmen.entity.Course;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "course",type = "course")
public class EsCourse implements Serializable {

    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long courseId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text,index = false)
    private String pictureuri;
    @Field(type = FieldType.Text)
    private String teacher;
    @Field(type = FieldType.Text)
    private String summary;
    @Field(type = FieldType.Integer,index = false)
    private int sumplay;
    @Field(type = FieldType.Integer,index = false)
    private int sumstar;
    @Field(type = FieldType.Text,index = false)
    private String srcuri;

    protected EsCourse() {
    }

    public EsCourse(Course course) {
        this.title = course.getTitle();
        this.pictureuri = course.getPictureuri();
        this.teacher = course.getTeacher();
        this.summary = course.getSummary();
        this.sumplay = course.getSumplay();
        this.sumstar = course.getSumstar();
        this.srcuri = course.getSrcuri();
    }

    public EsCourse(String title, String pictureuri, String teacher, String summary, int sumplay, int sumstar, String srcuri) {
        this.title = title;
        this.pictureuri = pictureuri;
        this.teacher = teacher;
        this.summary = summary;
        this.sumplay = sumplay;
        this.sumstar = sumstar;
        this.srcuri = srcuri;
    }

    public void update(Course course) {
        this.title = course.getTitle();
        this.pictureuri = course.getPictureuri();
        this.teacher = course.getTeacher();
        this.summary = course.getSummary();
        this.sumplay = course.getSumplay();
        this.sumstar = course.getSumstar();
        this.srcuri = course.getSrcuri();
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

    public String getSrcuri() {
        return srcuri;
    }

    public void setSrcuri(String srcuri) {
        this.srcuri = srcuri;
    }
}
