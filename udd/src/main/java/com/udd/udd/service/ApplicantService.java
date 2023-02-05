package com.udd.udd.service;

import com.udd.udd.model.Applicant;
import com.udd.udd.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService {

    @Autowired
    ApplicantRepository applicantRepository;

    public void save(Applicant applicant){
        applicantRepository.save(applicant);
    }

    public Applicant findById(String id){
        return (Applicant) applicantRepository.findById(id).orElse(null);
    }
}
