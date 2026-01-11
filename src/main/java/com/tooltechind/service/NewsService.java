package com.tooltechind.service;

import com.tooltechind.dto.NewsDto;
import com.tooltechind.entity.News;
import com.tooltechind.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    
    private final NewsRepository newsRepository;
    
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    
    public List<NewsDto> getAll() {
        return newsRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    public NewsDto create(NewsDto dto) {
        News news = new News();  // Empty constructor
        news.setTitle(dto.title());
        news.setDescription(dto.description());
        news.setEventDate(dto.eventDate());
        news.setImageUrl(dto.imageUrl());
        News saved = newsRepository.save(news);
        return toDto(saved);
    }
    
    public NewsDto update(Long id, NewsDto dto) {
    	  News news = newsRepository.findById(id).orElseThrow();
    	  news.setTitle(dto.title());
    	  news.setDescription(dto.description());
    	  news.setEventDate(dto.eventDate());
    	  news.setImageUrl(dto.imageUrl());
    	  return toDto(newsRepository.save(news));
    	}

    	public void delete(Long id) {
    	  newsRepository.deleteById(id);
    	}

    
    private NewsDto toDto(News news) {
        return new NewsDto(
            news.getId(),
            news.getTitle(),
            news.getDescription(),
            news.getEventDate(),
            news.getImageUrl(),
            news.getCreatedAt()
        );
    }
    
    
}
