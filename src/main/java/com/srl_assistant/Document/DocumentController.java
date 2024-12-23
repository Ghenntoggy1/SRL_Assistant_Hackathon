package com.srl_assistant.Document;

import com.srl_assistant.Templates.Template;
import com.srl_assistant.Templates.TemplateRepository;
import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentValidationService documentValidationService;
    @Autowired
    private PDFTextExtractor pdfTextExtractor;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private DocumentService documentService;

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<ValidationResult>> validateDocument(
            @RequestParam("document") Integer fileId,
            @RequestParam("tag") String fileType) {
        try {

            Document document = documentRepository.findById(fileId)
                    .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + fileId));

            ValidationResult result = documentValidationService.validateDocument(document.getLinkMinio(), fileType, fileId);

            return ResponseEntity.ok(ResponseHelper.success(result, "Document validated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.error("Validation failed: " + e.getMessage(), 500));
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getDocumentsByUserId(@PathVariable Integer userId) {

        ApiResponse<List<DocumentDTO>> response = documentService.getDocumentsByUserId(userId);
        if (response.getErrorCode() == 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{userId}/{fileId}")
    public ResponseEntity<ApiResponse<DocumentDTO>> getDocumentById(@PathVariable Integer userId, @PathVariable Integer fileId) {
        ApiResponse<DocumentDTO> response = documentService.getDocumentById(userId, fileId);
        if (response.getErrorCode() == 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
