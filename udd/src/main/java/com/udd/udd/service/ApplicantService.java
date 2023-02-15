package com.udd.udd.service;

import com.udd.udd.dto.RegisterDTO;
import com.udd.udd.model.Applicant;
import com.udd.udd.model.Location;
import com.udd.udd.repository.ApplicantRepository;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

@Service
public class ApplicantService {

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    LocationService locationService;

    private final Path root = Paths.get("uploads");

    private static int applicantNum = 1;

    public void save(Applicant applicant){
        applicantRepository.save(applicant);
    }

    public Applicant findById(String id){
        return (Applicant) applicantRepository.findById(id).orElse(null);
    }

    public Applicant register(RegisterDTO dto) throws Exception {
        MultipartFile cv = dto.getCv();
        MultipartFile coverLetter = dto.getCoverLetter();
        String cvContent = saveAndReadPdf(cv);
        String clContent = saveAndReadPdf(coverLetter);

        Applicant a = new Applicant();
        a.setId(String.valueOf(ApplicantService.applicantNum));
        a.setFirstName(dto.getFirstName());
        a.setLastName(dto.getLastName());
        a.setEducation(dto.getEducation());
        a.setClContent(clContent);
        a.setCvContent(cvContent);

        Location l = locationService.getLocationFromAddress(dto.getCity());
        GeoPoint g = new GeoPoint(l.getLat(),l.getLon());
        a.setLocation(g);

        ++ApplicantService.applicantNum;
        save(a);

        System.out.println(l.getLat() + "-----" + l.getLon());
        return a;
    }


    public String saveAndReadPdf(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        File resourcesDir = new File("src/main/resources");
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
        }

        File pdfFile = new File(resourcesDir, fileName);
        FileOutputStream outputStream = new FileOutputStream(pdfFile);

        FileCopyUtils.copy(inputStream, outputStream);

        inputStream.close();
        outputStream.close();


        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        cosDoc.close();

        return parsedText;
    }
}
