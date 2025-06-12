package net.supercoding.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploader {

    private final String uploadDir = "/my/local/upload/path"; // 혹은 properties 설정

    public String upload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + File.separator + fileName);
            file.transferTo(dest);
            return "/images/" + fileName; // 클라이언트에 제공할 접근 가능한 URL
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}
