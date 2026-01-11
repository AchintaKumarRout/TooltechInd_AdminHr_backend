package com.tooltechind.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewsDto(
    Long id,
    String title,
    String description,
    LocalDate eventDate,
    String imageUrl,
    LocalDateTime createdAt
) {}
