package com.tooltechind.controller;



import com.tooltechind.dto.JobDTO;
import com.tooltechind.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    // 👇 ADD THESE JOB ENDPOINTS
    @GetMapping("hr/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping("hr/jobs")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.createJob(jobDTO));
    }

    @PutMapping("hr/jobs/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
    }

    @DeleteMapping("hr/jobs/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }
    // 👆 END JOB ENDPOINTS
}
