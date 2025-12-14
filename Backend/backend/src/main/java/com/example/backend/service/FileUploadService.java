package com.example.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backend.entity.UploadedFile;
import com.example.backend.repository.UploadedFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileUploadService {

    private final Cloudinary cloudinary;
    private final UploadedFileRepository fileRepository;

    public FileUploadService(Cloudinary cloudinary, UploadedFileRepository fileRepository) {
        this.cloudinary = cloudinary;
        this.fileRepository = fileRepository;
    }

    /**
     * Uploads file as RAW to Cloudinary, saves DB record with semester/year/courseName.
     */
    public Map<String, Object> upload(MultipartFile file,
                                      String semester,
                                      String year,
                                      String courseName) throws Exception {

        // 1) Upload to Cloudinary as raw
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("resource_type", "raw")
        );

        String publicId = (String) uploadResult.get("public_id");
        String secureUrl = (String) uploadResult.get("secure_url");

        // 2) Save to DB
        UploadedFile record = new UploadedFile();
        record.setPublicId(publicId);
        record.setFileUrl(secureUrl);
        record.setSemester(semester);
        record.setYear(year);
        record.setCourseName(courseName);

        fileRepository.save(record);

        // 3) Response
        return Map.of(
                "message", "File uploaded & saved successfully",
                "fileUrl", secureUrl,
                "publicId", publicId,
                "metadata", Map.of(
                        "semester", semester,
                        "year", year,
                        "courseName", courseName
                )
        );
    }
}
