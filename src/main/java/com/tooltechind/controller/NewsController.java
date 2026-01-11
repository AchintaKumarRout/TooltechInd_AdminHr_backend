package com.tooltechind.controller;

import com.tooltechind.dto.NewsDto;
import com.tooltechind.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsController {
    
    private final NewsService newsService;
    
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    
//    @GetMapping("/news")
//    public ResponseEntity<List<NewsDto>> getAllNews() {
//        return ResponseEntity.ok(newsService.getAll());
//    }
    @GetMapping("/admin/news")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NewsDto>> getAllNewsByAdmin() {
        return ResponseEntity.ok(newsService.getAll());
    
    }
    @PostMapping("/admin/news")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto dto) {
        return ResponseEntity.ok(newsService.create(dto));
    }
    
    @PutMapping("admin/news/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long id, @RequestBody NewsDto dto) {
      return ResponseEntity.ok(newsService.update(id, dto));
    }

    @DeleteMapping("admin/news/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
      newsService.delete(id);
      return ResponseEntity.noContent().build();
    }


}
