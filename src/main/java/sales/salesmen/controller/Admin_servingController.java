package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.SCatalog;
import sales.salesmen.entity.SCatalog2;
import sales.salesmen.entity.Serving;
import sales.salesmen.service.SCatalogService;
import sales.salesmen.service.ServingService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admins/serving")
public class Admin_servingController {


    @Autowired
    ServingService servingService;

    @Autowired
    SCatalogService sCatalogService;

    @GetMapping
    public String getArticle(@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             Model model) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Serving> servingspage = servingService.getServingByPage(pageable);
        List<Serving> servings = servingspage.getContent();

        model.addAttribute("page", pageIndex + 1);
        model.addAttribute("servings", servings);

        return "/admin/serving_list";

    }

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

    @PostMapping("/addserving")
    public @ResponseBody
    JSONObject addaddserving(@RequestParam("catalog2id") int catalog2id,
                             @RequestParam("title") String title,
                             @RequestParam("subtitle") String subtitle,
                             @RequestParam(value = "pic",required = false) MultipartFile file,
                             @RequestParam("summary") String summary,
                             @RequestParam("price") String price) {

        JSONObject responejson = new JSONObject();

        servingService.saveServing(catalog2id, title, subtitle, file, summary, price);
        responejson.put("error", 0);
        return responejson;
    }

    @PostMapping("/delete")
    public @ResponseBody
    JSONObject deleteServing(@RequestParam("servingid") long servingid) {
        JSONObject responsejson = new JSONObject();

        if (servingService.removeServing(servingid) == true) {
            responsejson.put("error", 0);
            return responsejson;
        } else {
            responsejson.put("error", 1);
            responsejson.put("msg", "服务不存在");
            return responsejson;
        }
    }

    @GetMapping("/getserving")
    public @ResponseBody
    String getserving(@RequestParam("servingid") long servingid) {
        Serving serving = servingService.getServingById(servingid);
        if (serving.getPrice().startsWith("价格 ：")) {
            serving.setPrice(serving.getPrice().substring(4));
        }
        return JSONObject.toJSONString(serving);
    }

    @PostMapping("/changeserving")
    public @ResponseBody
    JSONObject changeserving(@RequestParam("servingid") long servingid,
                             @RequestParam("catalog2id") int catalog2id,
                             @RequestParam("title") String title,
                             @RequestParam("subtitle") String subtitle,
                             @RequestParam(value = "pic", required = false) MultipartFile file,
                             @RequestParam("summary") String summary,
                             @RequestParam("price") String price) {

        JSONObject responejson = new JSONObject();

        servingService.updateServing(servingid, catalog2id, title, subtitle, file, summary, price);
        responejson.put("error", 0);
        return responejson;
    }


}
