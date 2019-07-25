package sales.salesmen.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "作者为空")
    @Column
    private String author;

    @NotEmpty(message = "标题为空")
    @Column
    private String title;

    @Lob
    @NotEmpty(message = "content为空")
    @Column
    private String content;

    @Lob
    @NotEmpty(message = "内容为空")
    @Column
    private String htmlContent;

    private String timeDifference;

    @OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name = "acatalog_id")
    private ACatalog aCatalog;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "article_comment",joinColumns = @JoinColumn(name = "article_id",referencedColumnName = "id"))
    private List<Comment> comments;

    @Column
    private Integer commentSize=0;

    @Column
    private String avatar;

    @Column(name = "createtime")
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    protected Article() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article(@NotEmpty(message = "作者为空") String author, @NotEmpty(message = "标题为空") String title, @NotEmpty(message = "内容为空") String htmlContent, String avatar) {
        this.author = author;
        this.title = title;
        this.htmlContent = htmlContent;
        this.avatar = avatar;
    }

    public Article(@NotEmpty(message = "作者为空") String author, @NotEmpty(message = "标题为空") String title, @NotEmpty(message = "content为空") String content, @NotEmpty(message = "内容为空") String htmlContent, String avatar) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.htmlContent = htmlContent;
        this.avatar = avatar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public ACatalog getaCatalog() {
        return aCatalog;
    }

    public void setaCatalog(ACatalog aCatalog) {
        this.aCatalog = aCatalog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = comments.size();
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    public void removeComment(Long commentId){
        for (int i=0;i<comments.size();i++){
            if (comments.get(i).getId()==commentId){
                this.comments.remove(i);
                break;
            }
        }

        this.commentSize = this.comments.size();
    }
}
