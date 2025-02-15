package com.knu.object_recognition.service;

import com.knu.object_recognition.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
}
