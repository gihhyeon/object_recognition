package com.knu.object_recognition.entity;

import com.knu.object_recognition.dto.FileDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "upload_date", updatable = false, insertable = false)
    private Instant uploadDate;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    public FileEntity(FileDTO fileDTO) {
        this.fileName = fileDTO.getFileName();
        this.fileUrl = fileDTO.getFileUrl();
    }
}
