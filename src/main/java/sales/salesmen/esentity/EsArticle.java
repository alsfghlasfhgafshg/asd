package sales.salesmen.esentity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import sales.salesmen.entity.Article;

import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "article",type = "article")
public class EsArticle implements Serializable {

    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long articleId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String author;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text,index = false)
    private String timeDifference;
    @Field(type = FieldType.Text,index = false)
    private String avatar;
    @Field(type = FieldType.Integer,index = false)
    private Integer commentSize;

    protected EsArticle() {
    }

    public EsArticle(Long articleId, String title, String author, String content, String avatar, Integer commentSize) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.avatar = avatar;
        this.commentSize = commentSize;
    }

    public EsArticle(Article article) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.content = article.getContent();
        this.avatar = article.getAvatar();
        this.commentSize = article.getCommentSize();
    }

    public void update(Article article) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.content = article.getContent();
        this.avatar = article.getAvatar();
        this.commentSize = article.getCommentSize();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }
}
