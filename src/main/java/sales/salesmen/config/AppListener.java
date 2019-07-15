package sales.salesmen.config;

import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import sales.salesmen.entity.Authority;
import sales.salesmen.entity.SCatalog;
import sales.salesmen.entity.SCatalog2;
import sales.salesmen.repository.AuthorityRepository;
import sales.salesmen.repository.SCatalog2Repository;
import sales.salesmen.repository.SCatalogRepository;


public class AppListener implements ApplicationListener {

    Logger log = LoggerFactory.getLogger(AppListener.class);

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    SCatalogRepository sCatalogRepository;

    @Autowired
    SCatalog2Repository sCatalog2Repository;


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextStartedEvent) {
            log.info("Application Event:{}", "ContextStartedEvent");
        }
        if (event instanceof ContextRefreshedEvent) {
            log.info("Application Event:{}", "ContextRefreshedEvent");
        }
        if (event instanceof ContextClosedEvent) {
            log.info("Application Event:{}", "ContextClosedEvent");
        }
        if (event instanceof ContextStoppedEvent) {
            log.info("Application Event:{}", "ContextStoppedEvent");
        }
        if (event instanceof ApplicationReadyEvent) {

            log.info("Application Event:{}", "ApplicationReadyEvent");

            //添加/更新 权限表数据
            authorityRepository.save(new Authority(1L, "管理员", "ROLE_ADMIN"));
            authorityRepository.save(new Authority(2L, "销售员", "ROLE_SALES"));
            authorityRepository.save(new Authority(3L, "顾客", "ROLE_USER"));


            //添加/更新 服务分类表数据
            SCatalog touzizhiye = new SCatalog(1, "投资置业");
            SCatalog haiwaiyimin = new SCatalog(2, "海外移民");
            SCatalog haiwailiuxue = new SCatalog(3, "海外留学");
            SCatalog yiliaojiankang = new SCatalog(4, "医疗健康");
            SCatalog jiaoyupeixun = new SCatalog(5, "教育培训");

            sCatalogRepository.save(touzizhiye);
            sCatalogRepository.save(haiwaiyimin);
            sCatalogRepository.save(haiwailiuxue);
            sCatalogRepository.save(yiliaojiankang);
            sCatalogRepository.save(jiaoyupeixun);

            sCatalog2Repository.save(new SCatalog2(1, "泰国房产", touzizhiye));
            sCatalog2Repository.save(new SCatalog2(2, "中国房产", touzizhiye));
            sCatalog2Repository.save(new SCatalog2(3, "美国房产", touzizhiye));


        }

    }
}
