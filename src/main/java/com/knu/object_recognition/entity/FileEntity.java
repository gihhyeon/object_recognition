package com.knu.object_recognition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "upload_date")
    private Instant uploadDate;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;
}
