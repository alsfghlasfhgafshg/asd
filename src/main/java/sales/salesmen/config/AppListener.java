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
import sales.salesmen.entity.*;
import sales.salesmen.repository.*;


public class AppListener implements ApplicationListener {

    Logger log = LoggerFactory.getLogger(AppListener.class);

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    SCatalogRepository sCatalogRepository;

    @Autowired
    SCatalog2Repository sCatalog2Repository;

    @Autowired
    CCatalogRepository cCatalogRepository;

    @Autowired
    CCatalog2Repository cCatalog2Repository;

    @Autowired
    PCatalogRepository pCatalogRepository;

    @Autowired
    ACatalogRepository aCatalogRepository;


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


            CCatalog shipinkecheng = new CCatalog(1, "视频课程");
            CCatalog yinpinkecheng = new CCatalog(2, "音频课程");
            CCatalog wendangkecheng = new CCatalog(3, "文档课程");
            CCatalog waibulianjie = new CCatalog(4, "外部链接");

            CCatalog2 jijin = new CCatalog2(1, "基金");
            CCatalog2 gupiao = new CCatalog2(2, "股票");
            CCatalog2 haiwaizichanpeizhi = new CCatalog2(3, "海外资产配置");
            CCatalog2 xilieke = new CCatalog2(4, "系列课");
            CCatalog2 qita = new CCatalog2(5, "其他");

            cCatalogRepository.save(shipinkecheng);
            cCatalogRepository.save(yinpinkecheng);
            cCatalogRepository.save(wendangkecheng);
            cCatalogRepository.save(waibulianjie);

            cCatalog2Repository.save(jijin);
            cCatalog2Repository.save(gupiao);
            cCatalog2Repository.save(haiwaizichanpeizhi);
            cCatalog2Repository.save(xilieke);
            cCatalog2Repository.save(qita);

            PCatalog simujijin = new PCatalog(1,"私募基金");
            PCatalog gongmujijin = new PCatalog(2,"公募基金");
            PCatalog xianjinguanli = new PCatalog(3,"现金管理");
            PCatalog leigushou = new PCatalog(4,"类固收");
            PCatalog qitachanpin = new PCatalog(5,"其他");

            pCatalogRepository.save(simujijin);
            pCatalogRepository.save(gongmujijin);
            pCatalogRepository.save(xianjinguanli);
            pCatalogRepository.save(leigushou);
            pCatalogRepository.save(qitachanpin);

            ACatalog jinronghuati = new ACatalog(6,"金融话题");
            ACatalog touzijineng = new ACatalog(7,"投资技能");
            ACatalog dakaguandian = new ACatalog(8,"大咖观点");
            ACatalog shichanghuodong = new ACatalog(9,"市场活动");

            aCatalogRepository.save(jinronghuati);
            aCatalogRepository.save(touzijineng);
            aCatalogRepository.save(dakaguandian);
            aCatalogRepository.save(shichanghuodong);



        }

    }
}
