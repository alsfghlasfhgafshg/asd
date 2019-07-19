package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sales.salesmen.entity.*;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.CourseService;
import sales.salesmen.service.SCatalogService;
import sales.salesmen.service.ServingService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/serving")
public class Home_servingController {

    @Autowired
    ServingService servingService;

    @Autowired
    SCatalogService sCatalogService;


    @GetMapping("/getscatalog")
    public @ResponseBody
    List<SCatalog> getallScatalog() {
        return sCatalogService.getAllSCatalog();
    }

    @GetMapping("/getscatalogbyid")
    public @ResponseBody
    Serving getoneScatalog(@RequestParam("servingid") long servingid) {
        return servingService.getServingById(servingid);
    }


    @GetMapping("/getscatalog2")
    public @ResponseBody
    List<SCatalog2> getallScatalog2(@RequestParam("scatalogid") int scatalogid) {
        SCatalog sCatalog = sCatalogService.getSCatalogById(scatalogid);
        if (sCatalog != null) {
            return sCatalogService.getAllSCatalog2(sCatalog);
        } else {
            return new ArrayList<>();
        }

    }

    @GetMapping("/getserving")
    public @ResponseBody
    List<Serving> getserving(@RequestParam("scatalog2") int scatalog2,
                             @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Serving> servingPageBySCatalog2 = servingService.getServingPageBySCatalog2(sCatalogService.getSCatalog2(scatalog2), page);
        List<Serving> content = servingPageBySCatalog2.getContent();
        return content;
    }


}
