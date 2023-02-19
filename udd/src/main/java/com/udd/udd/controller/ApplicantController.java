package com.udd.udd.controller;

import com.udd.udd.dto.DownloadFileDTO;
import com.udd.udd.dto.RegisterDTO;
import com.udd.udd.model.IndexUnit;
import com.udd.udd.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("applicant")
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicantController {

    @Autowired
    ApplicantService applicantService;

    @Autowired
    private HttpServletResponse response;

    @PostMapping
    public void save(@RequestBody IndexUnit applicant){
        applicantService.save(applicant);
    }

    @GetMapping("/{id}")
    public IndexUnit findById(@PathVariable String id){
        return applicantService.findById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Save the file to disk or process it in memory
        System.out.println(file);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }


    @PostMapping(value = "/register", consumes = { "multipart/form-data" })
    public ResponseEntity<?> register(@ModelAttribute RegisterDTO dto) throws Exception {
        System.out.println(dto.getCv());
        IndexUnit a = applicantService.register(dto);
        if(a == null){
            return new ResponseEntity<>("Failed registration!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success registration", HttpStatus.OK);
    }

    @PostMapping("/download")
    @ResponseBody public ResponseEntity<?> downloadFile(@RequestBody DownloadFileDTO dto, HttpServletResponse response) {
        Boolean downloaded = applicantService.downloadFile(dto.getId(),dto.getIsCV());
        if(downloaded) {
            return new ResponseEntity<>("Success downloading!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed downloading!!", HttpStatus.BAD_REQUEST);
    }

}
