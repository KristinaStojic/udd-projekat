package com.udd.udd.service;

import com.udd.udd.dto.RegisterDTO;
import com.udd.udd.model.Applicant;
import com.udd.udd.model.IndexUnit;
import com.udd.udd.model.Location;
import com.udd.udd.repository.ApplicationRepository;
import com.udd.udd.repository.IndexUnitRepository;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ApplicantService {

    @Autowired
    IndexUnitRepository indexUnitRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    private HttpServletResponse response;

    private final Path root = Paths.get("uploads");

    private static int applicantNum = 1;

    public void save(IndexUnit applicant){
        indexUnitRepository.save(applicant);
    }

    public IndexUnit findById(String id){
        return (IndexUnit) indexUnitRepository.findById(id).orElse(null);
    }

    public IndexUnit register(RegisterDTO dto) throws Exception {
        MultipartFile cv = dto.getCv();
        MultipartFile coverLetter = dto.getCoverLetter();
        String cvContent = saveAndReadPdf(cv);
        String clContent = saveAndReadPdf(coverLetter);

        Applicant a = new Applicant();
        a.setFirstName(dto.getFirstName());
        a.setLastName(dto.getLastName());
        a.setCvName(cv.getOriginalFilename());
        a.setClName(coverLetter.getOriginalFilename());
        a.setEducation(dto.getEducation());
        a.setStreet(dto.getStreet());
        a.setCity(dto.getCity());
        applicationRepository.save(a);

        IndexUnit iu = new IndexUnit();
        //iu.setId(String.valueOf(ApplicantService.applicantNum));
        iu.setId(a.getId().toString());
        iu.setFirstName(dto.getFirstName());
        iu.setLastName(dto.getLastName());
        iu.setEducation(dto.getEducation());
        iu.setClContent(clContent);
        iu.setCvContent(cvContent);

        Location l = locationService.getLocationFromAddress(dto.getCity());
        GeoPoint g = new GeoPoint(l.getLat(),l.getLon());
        iu.setLocation(g);

        ++ApplicantService.applicantNum;
        save(iu);

        System.out.println(l.getLat() + "-----" + l.getLon());
        return iu;
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

    public Boolean downloadFile(String id, Boolean isCV){
        Applicant a = applicationRepository.findById(Long.valueOf(id)).orElseGet(null);

        try {
            String filename;

            if(isCV){
                filename = a.getCvName();
            }else{
                filename = a.getClName();
            }

            ClassPathResource resource  = new ClassPathResource(filename);
            InputStream inputStream = resource.getInputStream();

            File file = new File("src/main/resources/downloads/" + filename);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=" + file);
            response.setContentLength((int) file.length());

            Path path = Paths.get(file.getAbsolutePath());
            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
