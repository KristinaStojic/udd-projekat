package com.udd.udd.controller;

import com.udd.udd.model.Applicant;
import com.udd.udd.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("applicant")
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
}
