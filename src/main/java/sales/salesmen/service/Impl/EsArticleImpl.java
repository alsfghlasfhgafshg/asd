package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import sales.salesmen.esentity.EsArticle;
import sales.salesmen.repository.es.EsArticleRepository;
import sales.salesmen.service.EsArticleService;


@Service
public class EsArticleImpl implements EsArticleService {

    @Autowired
    private EsArticleRepository esArticleRepository;

    @Override
    public void removeEsArticle(String id) {
        esArticleRepository.deleteById(id);
    }

    @Override
    public EsArticle updateEsArticle(EsArticle esArticle) {
        return esArticleRepository.save(esArticle);
    }

    @Override
    public EsArticle getEsArticleByArticleId(Long articleId) {
        return esArticleRepository.findByArticleId(articleId);
    }

    @Override
    public Page<EsArticle> listEsArticle(Pageable pageable) {
        return esArticleRepository.findAll(pageable);
    }

    @Override
    public Page<EsArticle> listNewestEsArticles(String keyword, Pageable pageable) {
        Page<EsArticle> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"articleId");
        if (pageable.getSort().isUnsorted()){
            pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }

        pages = esArticleRepository.findByTitleContainingOrAuthorContainingOrContentContaining(
                keyword,keyword,keyword,pageable
        );
        return pages;
    }
}
