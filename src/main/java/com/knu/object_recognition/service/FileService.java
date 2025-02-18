package com.knu.object_recognition.service;

import com.knu.object_recognition.dto.FileDTO;
import com.knu.object_recognition.entity.FileEntity;
import com.knu.object_recognition.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/Desktop/uploads/";

    private final FileRepository fileRepository;

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "파일이 비어 있습니다.";
        }

        try {
            // 바탕화면에 uploads 폴더 생성 (없다면 생성)
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 원본 파일명 가져오기
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                return "파일 이름을 가져올 수 없습니다.";
            }

            // 저장할 파일 경로 설정
            Path filePath = uploadPath.resolve(originalFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // DTO 생성 후 DB 저장
            FileDTO fileDTO = new FileDTO(originalFileName, filePath.toString());
            FileEntity fileEntity = new FileEntity(fileDTO);
            fileRepository.save(fileEntity);

            return "파일 업로드 성공: " + filePath.toString();
        } catch (IOException e) {
            return "파일 업로드 실패: " + e.getMessage();
        }
    }
}
