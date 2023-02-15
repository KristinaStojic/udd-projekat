package com.udd.udd.controller;

import com.udd.udd.dto.RegisterDTO;
import com.udd.udd.model.Applicant;
import com.udd.udd.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("applicant")
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicantController {

    @Autowired
    ApplicantService applicantService;

    @PostMapping
    public void save(@RequestBody Applicant applicant){
        applicantService.save(applicant);
    }

    @GetMapping("/{id}")
    public Applicant findById(@PathVariable String id){
        return applicantService.findById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Save the file to disk or process it in memory
        System.out.println(file);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }


    @PostMapping(value = "/register", consumes = { "multipart/form-data" })
    public ResponseEntity<?> register(@ModelAttribute RegisterDTO dto) {
        System.out.println(dto.getCv());
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
}
