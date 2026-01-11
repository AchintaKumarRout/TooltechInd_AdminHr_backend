package com.tooltechind.dto;



import java.time.LocalDateTime;

public class JobDTO {
    private Long id;
    private String title;
    private String location;
    private String experience;
    private String type;
    private String description;
    private String qualification;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public JobDTO() {}

    // From Entity constructor
    public JobDTO(Long id, String title, String location, String experience, 
                  String type, String description, String qualification,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.experience = experience;
        this.type = type;
        this.description = description;
        this.qualification = qualification;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
