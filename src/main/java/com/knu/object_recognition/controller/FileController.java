package com.knu.object_recognition.controller;

import com.knu.object_recognition.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
}
