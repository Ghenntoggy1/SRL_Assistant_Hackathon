package com.srl_assistant.Templates;

import com.srl_assistant.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/templates")
    public ResponseEntity<ApiResponse<List<TemplateDTO>>> getAllTemplates() {
        ApiResponse<List<TemplateDTO>> response = templateService.getAllTemplates();
        if (response.getErrorCode() == 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/templates/{templateId}")
    public ResponseEntity<ApiResponse<TemplateDTO>> getTemplateById(@PathVariable Integer templateId) {
        ApiResponse<TemplateDTO> response = templateService.getTemplateById(templateId);
        if (response.getErrorCode() == 0) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

