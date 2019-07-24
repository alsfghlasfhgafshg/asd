package sales.salesmen.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ArticleLookedUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long shared_user_id;

    @Column
    private Long user_id;

    @Column
    private Long article_id;

    protected ArticleLookedUser() {
    }

    public Long getShared_user_id() {
        return shared_user_id;
    }

    public void setShared_user_id(Long shared_user_id) {
        this.shared_user_id = shared_user_id;
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
