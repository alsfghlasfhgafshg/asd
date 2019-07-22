package sales.salesmen.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sales.salesmen.entity.Products;
import sales.salesmen.service.PCatalogService;
import sales.salesmen.service.ProductsService;
import sales.salesmen.vo.Response;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admins/products")
public class Admin_productsController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private PCatalogService pCatalogService;

    @GetMapping
    public ModelAndView getProducts(@RequestParam(value = "async",required = false)boolean async,
                                   @RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                                   @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                   Model model){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<Products> page = productsService.listProducts(pageable);
        List<Products> list = page.getContent();
        model.addAttribute("page",page);
        model.addAttribute("productList",list);
        return new ModelAndView(async?"admin/product_list :: #mainContainerRepleace" : "admin/product_list",
                "productModel",model);
    }

    @GetMapping("/add")
    public ModelAndView addProduct(Model model){
        model.addAttribute("product",new Products(null,null,null,null,null,null,null));
        return new ModelAndView("/admin/add_product","productModel",model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateProduct(@RequestBody JSONObject fastjson) {
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd" );
        Long id = fastjson.getLong("id");
        Products products;
        if (id!=null){
            products = productsService.findProductById(fastjson.getLong("id")).get();
            products.setName(fastjson.getString("name"));
            products.setScale(fastjson.getString("scale"));
            products.setStartmoney(fastjson.getString("startmoney"));
            products.setInvetmentPeriod(fastjson.getString("invetmentperiod"));
            products.setPerformance(fastjson.getString("performance"));
            products.setStartDate(sdf1.format(fastjson.getDate("startDate")));
            products.setType(fastjson.getString("type"));
            products.setpCatalog(pCatalogService.findCatalogById(fastjson.getInteger("pcatalogId")).get());
        }else {
            products = new Products(fastjson.getString("name"),
                    fastjson.getString("scale"),
                    fastjson.getString("startmoney"),
                    fastjson.getString("invetmentperiod"),
                    fastjson.getString("performance"),
                    sdf1.format(fastjson.getDate("startDate")),
                    fastjson.getString("type"));
            products.setpCatalog(pCatalogService.findCatalogById(fastjson.getInteger("pcatalogId")).get());
        }
        productsService.saveProducts(products);
        return ResponseEntity.ok().body(new Response(true,"创建产品成功",null));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id")Long id, Model model){
        Optional<Products> products = productsService.findProductById(id);
        model.addAttribute("product",products.get());
        return new ModelAndView("/admin/add_product","productModel",model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id")Long id){
        try{
            productsService.removeProductById(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"删除成功!"));
    }

}
