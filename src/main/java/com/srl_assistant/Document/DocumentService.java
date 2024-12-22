package com.srl_assistant.Document;

import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public ApiResponse<List<DocumentDTO>> getDocumentsByUserId(Integer userId) {
        try {
            List<DocumentDTO> documents = documentRepository.findByUserId(userId).stream()
                    .map(DocumentDTO::fromEntity)
                    .toList();
            if (documents.isEmpty()) {
                return ResponseHelper.success(Collections.emptyList(), "No documents found for this user");
            }
            return ResponseHelper.success(documents, "Documents retrieved successfully");
        } catch (Exception e) {
            return ResponseHelper.error("An error occurred while retrieving documents", 500);
        }
    }


    public ApiResponse<DocumentDTO> getDocumentById(Integer userId, Integer documentId) {
        try {
            Document document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + documentId));
            return ResponseHelper.success(DocumentDTO.fromEntity(document), "Document retrieved successfully");
        } catch (Exception e) {
            return ResponseHelper.error("An error occurred while retrieving the document", 500);
        }
    }
}
