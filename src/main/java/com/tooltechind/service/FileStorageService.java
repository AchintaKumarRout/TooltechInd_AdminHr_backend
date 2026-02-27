package com.tooltechind.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadCategoryImage(MultipartFile file);
    String uploadNewsImage(MultipartFile file);  // new method
}