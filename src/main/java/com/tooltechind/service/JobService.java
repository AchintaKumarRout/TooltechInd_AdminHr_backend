package com.tooltechind.service;



import com.tooltechind.dto.JobDTO;
import com.tooltechind.entity.Job;
import com.tooltechind.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public JobDTO createJob(JobDTO jobDTO) {
        Job job = convertToEntity(jobDTO);
        Job savedJob = jobRepository.save(job);
        return convertToDTO(savedJob);
    }

    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        existingJob.setTitle(jobDTO.getTitle());
        existingJob.setLocation(jobDTO.getLocation());
        existingJob.setExperience(jobDTO.getExperience());
        existingJob.setType(jobDTO.getType());
        existingJob.setDescription(jobDTO.getDescription());
        existingJob.setQualification(jobDTO.getQualification());
        existingJob.setUpdatedAt(java.time.LocalDateTime.now());
        
        Job updatedJob = jobRepository.save(existingJob);
        return convertToDTO(updatedJob);
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }
        jobRepository.deleteById(id);
    }

    private JobDTO convertToDTO(Job job) {
        return new JobDTO(
            job.getId(),
            job.getTitle(),
            job.getLocation(),
            job.getExperience(),
            job.getType(),
            job.getDescription(),
            job.getQualification(),
            job.getCreatedAt(),
            job.getUpdatedAt()
        );
    }

    private Job convertToEntity(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setLocation(jobDTO.getLocation());
        job.setExperience(jobDTO.getExperience());
        job.setType(jobDTO.getType());
        job.setDescription(jobDTO.getDescription());
        job.setQualification(jobDTO.getQualification());
        return job;
    }
}
