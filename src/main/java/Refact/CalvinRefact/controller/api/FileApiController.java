package Refact.CalvinRefact.controller.api;

import Refact.CalvinRefact.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileApiController {

    @Autowired
    FileService fileService;

    @PostMapping("/api/img/upload")
    public ResponseEntity<Map<String, String>> imageUpload(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            String url = fileService.imageUploadReturnUrl(file);
            response.put("url", url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
