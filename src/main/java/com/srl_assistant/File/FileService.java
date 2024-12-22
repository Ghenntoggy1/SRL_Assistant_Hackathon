package com.srl_assistant.File;

import com.srl_assistant.Document.Document;
import com.srl_assistant.Document.DocumentRepository;
import com.srl_assistant.Document.DocumentTypeDetector;
import com.srl_assistant.User.User;
import com.srl_assistant.User.UserRepository;
import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentTypeDetector documentTypeDetector;

    @Value("${minio.bucketName}")
    private String bucketName;


    public ApiResponse<?> uploadFileWithUser(MultipartFile file, Integer userId) throws Exception {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String fileUrl = uploadFileToMinio(file, fileName);

        Document document = new Document();
        document.setName(fileName);
        document.setLinkMinio(fileUrl);

        if (userId != null) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                document.setUser(user);
            } else {
                throw new IllegalArgumentException("User not found for the provided user_id.");
            }
        }

        Document savedDocument = documentRepository.save(document);
        String documentType = documentTypeDetector.detectDocumentType(savedDocument.getLinkMinio());

        return ResponseHelper.success(
                new UploadResponse(savedDocument.getId(), fileUrl, documentType),
                "File uploaded successfully."
        );
    }

    private String uploadFileToMinio(MultipartFile file, String fileName) throws Exception {
        try{minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );}catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .expiry(60 * 60 * 24 * 7)
                        .build()
        );    }

}

