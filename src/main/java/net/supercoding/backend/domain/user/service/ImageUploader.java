//package net.supercoding.backend.domain.user.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ImageUploader {
//
//    private final String uploadDir = System.getProperty("user.dir") + "/uploads";
//
//    public String upload(MultipartFile file) {
//        File uploadPath = new File(uploadDir);
//        if (!uploadPath.exists()) {
//            boolean created = uploadPath.mkdirs();
//            if (!created) {
//                throw new RuntimeException("업로드 디렉토리 생성 실패");
//            }
//        }
//
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        File dest = new File(uploadPath, fileName);
//
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("이미지 업로드 실패", e);
//        }
//
//        return "/uploads/" + fileName;
//    }
//}
//
