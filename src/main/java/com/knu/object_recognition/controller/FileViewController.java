package com.knu.object_recognition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileViewController {

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";  // resources/templates/upload.html을 렌더링
    }
}