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

//    @PostMapping("/process")
//    public ResponseEntity<String> processFile(@RequestParam("fileName") String fileName) {
//        try {
//            Path originalFilePath = Paths.get(uploadDir, fileName);
//            if (!Files.exists(originalFilePath)) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일 없음");
//            }
//
//            // AI 처리가 완료된 파일 이름 (예: processed_원본파일명)
//            String processedFileName = "processed_" + fileName;
//            Path processedFilePath = Paths.get(uploadDir, processedFileName);
//
//            // 파일 복사 (AI 처리가 없으므로 복사로 대체)
//            Files.copy(originalFilePath, processedFilePath, StandardCopyOption.REPLACE_EXISTING);
//
//            return ResponseEntity.ok("처리 완료: " + processedFilePath.toAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 실패: " + e.getMessage());
//        }
//    }
//
//    // AI 처리된 파일 목록 반환 (processed_ 로 시작하는 파일만)
//    @GetMapping("/processed-list")
//    public ResponseEntity<List<String>> listProcessedFiles() {
//        File folder = new File(uploadDir);
//        if (!folder.exists() || !folder.isDirectory()) {
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//
//        // "processed_" 로 시작하는 파일만 필터링
//        String[] processedFiles = folder.list((dir, name) -> name.startsWith("processed_"));
//        return ResponseEntity.ok(Arrays.asList(processedFiles));
//    }
//
    // AI 처리된 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadProcessedFile(@RequestParam String fileName) {
        return fileService.downloadFile(fileName);
    }
}


