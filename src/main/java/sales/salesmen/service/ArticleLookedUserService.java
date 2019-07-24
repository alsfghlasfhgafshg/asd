package sales.salesmen.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sales.salesmen.entity.ArticleLookedUser;
import sales.salesmen.entity.User;
import sales.salesmen.repository.ArticleLookedUserRepository;
import sales.salesmen.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleLookedUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleLookedUserRepository articleLookedUserRepository;

    public void add(long shared_user_id, long user_id, long article_id) {
        if (shared_user_id != user_id) {
            ArticleLookedUser articleLookedUser = new ArticleLookedUser();
            articleLookedUser.setSharedUserId(shared_user_id);
            articleLookedUser.setUser_id(user_id);
            articleLookedUser.setArticle_id(article_id);

            articleLookedUserRepository.save(articleLookedUser);
        }
    }


    public List<User> allmyclient(Long uid) {
        List<ArticleLookedUser> allByShared_user = articleLookedUserRepository.findAllBySharedUserId(uid);

        List<Long> allclientid = new ArrayList<>();

        for (ArticleLookedUser articleLookedUser : allByShared_user) {
            allclientid.add(articleLookedUser.getUser_id());
        }

        List<User> allByIdIn = userRepository.findAllByIdIn(allclientid);
        return allByIdIn;
    }

}
