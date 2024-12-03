package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileController {

    @Autowired
    FileService fileService;
    //파일 다운로드
    @GetMapping("/download/{save_name}/{original_name}")
    public ResponseEntity DownloadFile(@PathVariable String save_name, @PathVariable String original_name){
        return fileService.FileDownload(save_name,original_name);
    }

    @GetMapping("/download/document/{save_name}/{original_name}")
    public ResponseEntity DownloadDoc(@PathVariable String save_name,@PathVariable String original_name){
        return fileService.DocumentFileDownload(save_name,original_name);
    }
}
