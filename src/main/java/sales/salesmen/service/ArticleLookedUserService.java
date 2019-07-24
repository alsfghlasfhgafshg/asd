package sales.salesmen.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.ArticleLookedUser;
import sales.salesmen.repository.ArticleLookedUserRepository;

@Service
public class ArticleLookedUserService {


    @Autowired
    ArticleLookedUserRepository articleLookedUserRepository;

    public void add(long shared_user_id, long user_id, long article_id) {
        if(shared_user_id!=user_id){
            ArticleLookedUser articleLookedUser = new ArticleLookedUser();
            articleLookedUser.setShared_user_id(shared_user_id);
            articleLookedUser.setUser_id(user_id);
            articleLookedUser.setArticle_id(article_id);

            articleLookedUserRepository.save(articleLookedUser);
        }
    }
}
