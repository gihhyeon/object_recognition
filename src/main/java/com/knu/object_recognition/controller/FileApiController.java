package com.knu.object_recognition.controller;

import com.knu.object_recognition.service.FileService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final String uploadDir = System.getProperty("user.dir") + File.separator + "uploads"; // 절대경로 지정

    // 파일 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs(); // 폴더 생성

            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // 파일 복사

            return ResponseEntity.ok("파일 업로드 성공: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> processFile(@RequestParam("fileName") String fileName) {
        try {
            Path originalFilePath = Paths.get(uploadDir, fileName);
            if (!Files.exists(originalFilePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일 없음");
            }

            // AI 처리가 완료된 파일 이름 (예: processed_원본파일명)
            String processedFileName = "processed_" + fileName;
            Path processedFilePath = Paths.get(uploadDir, processedFileName);

            // 파일 복사 (AI 처리가 없으므로 복사로 대체)
            Files.copy(originalFilePath, processedFilePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("처리 완료: " + processedFilePath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 실패: " + e.getMessage());
        }
    }

    // AI 처리된 파일 목록 반환 (processed_ 로 시작하는 파일만)
    @GetMapping("/processed-list")
    public ResponseEntity<List<String>> listProcessedFiles() {
        File folder = new File(uploadDir);
        if (!folder.exists() || !folder.isDirectory()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // "processed_" 로 시작하는 파일만 필터링
        String[] processedFiles = folder.list((dir, name) -> name.startsWith("processed_"));
        return ResponseEntity.ok(Arrays.asList(processedFiles));
    }

}
