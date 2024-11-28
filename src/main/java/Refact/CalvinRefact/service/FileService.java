package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.Subject;
import Refact.CalvinRefact.entity.entityEnum.YN;
import Refact.CalvinRefact.repository.FileDataJpaRepository;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    FileDataJpaRepository fileDataJpaRepository;
    @Autowired
    EntityManager em;

    //파일 저장
    @Transactional(rollbackFor = {IOException.class, IllegalStateException.class})
    public boolean saveFile(Board board, MultipartFile multipartFile) throws Exception {
        boolean result = false;

        String path = "F:\\CalvinUploadFiles\\";//로컬
//        String path = "/iceadmin/CalvinUploadFile/"; //서버
        //uuid 생성
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getName();
        File saveFile = new File(path, fileName);
        multipartFile.transferTo(saveFile);
        Refact.CalvinRefact.entity.File file = new Refact.CalvinRefact.entity.File(
                multipartFile.getName()
                , fileName
                , multipartFile.getSize()
                , YN.no
                , board);
        fileDataJpaRepository.save(file);
        if (em.contains(file)) {
            result = true;
        }
        return result;
    }
    @Transactional(rollbackFor = {IOException.class, IllegalStateException.class})
    public Refact.CalvinRefact.entity.File saveFile(MultipartFile multipartFile) throws Exception {

        String path = "F:\\CalvinUploadFiles\\";//로컬
//        String path = "/iceadmin/CalvinUploadFile/"; //서버
        //uuid 생성
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getName();
        File saveFile = new File(path, fileName);
        multipartFile.transferTo(saveFile);
        Refact.CalvinRefact.entity.File file = new Refact.CalvinRefact.entity.File(
                multipartFile.getName()
                , fileName
                , multipartFile.getSize()
                , YN.no
                );
        fileDataJpaRepository.save(file);

        return file;
    }

    //파일 삭제
    @Transactional(rollbackFor = {Exception.class})
    public void deleteFile(Refact.CalvinRefact.entity.File file) throws Exception{
        String path = "F:\\CalvinUploadFiles\\";//로컬
//        String path = "/iceadmin/CalvinUploadFile/"; //서버

        path += file.getSave_name();
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            file.setDeleted_date(LocalDateTime.now());
            file.setDelete_yn(YN.yes);
            em.flush();
        }
    }
    //파일 다운로드
    public ResponseEntity FileDownload(String save_name, String original_name){
        String path = "F:\\CalvinUploadFiles\\"+save_name; //로컬
//        String path = "/iceadmin/CalvinUploadFile/"+save_name;//서버
        try{
            UrlResource urlResource = new UrlResource("file:"+path);
            String encodedOriginalName = UriUtils.encode(original_name, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedOriginalName + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(urlResource);
        }catch(Exception e){
            // 사용자에게 오류 메시지 전송
            // 여기서는 간단히 "파일 다운로드 중 오류가 발생했습니다."라는 메시지를 반환하도록 설정
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 다운로드 중 오류가 발생했습니다.");
        }
    }
    public ResponseEntity FileDownload(String original_name){
        String path = "F:\\DocumentFile\\"+original_name; //로컬
//        String path = "/iceadmin/DocumentFile/"+original_name;//서버
        try{
            UrlResource urlResource = new UrlResource("file:"+path);
            String encodedOriginalName = UriUtils.encode(original_name, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedOriginalName + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(urlResource);
        }catch(Exception e){
            // 사용자에게 오류 메시지 전송
            // 여기서는 간단히 "파일 다운로드 중 오류가 발생했습니다."라는 메시지를 반환하도록 설정
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 다운로드 중 오류가 발생했습니다.");
        }
    }
}
