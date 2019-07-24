package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ArticleLookedUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sharedUserId;

    @Column
    private Long user_id;

    @Column
    private Long article_id;

    public ArticleLookedUser() {
    }

    public Long getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(Long sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }
}
