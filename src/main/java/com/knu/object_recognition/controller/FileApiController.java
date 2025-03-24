package com.knu.object_recognition.controller;

import com.knu.object_recognition.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    // 파일 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file);
            return ResponseEntity.ok("파일 업로드 성공: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }


    // AI 처리된 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadProcessedFile(@RequestParam String fileName) {
        return fileService.downloadFile(fileName);
    }
}


