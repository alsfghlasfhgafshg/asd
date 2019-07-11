package sales.salesmen.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.service.FileService;

@RestController
public class ImageController {

    @Autowired
    FileService fileService;

    //上传图片
    @PostMapping("/uploadimg")
    public JSONObject uploadImg(@RequestParam("img") MultipartFile file) {
        String filepath = fileService.uploadImage(file);
        JSONObject responsejson = new JSONObject();
        responsejson.put("default", filepath);
        return responsejson;
    }
}
