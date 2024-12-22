package com.srl_assistant.Templates;


import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public ApiResponse<List<TemplateDTO>> getAllTemplates() {
        try {
            List<TemplateDTO> templates = templateRepository.findAll().stream()
                    .map(TemplateDTO::fromEntity)
                    .toList();

            if (templates.isEmpty()) {
                return ResponseHelper.success(Collections.emptyList(), "No templates found");
            }
            return ResponseHelper.success(templates, "Templates retrieved successfully");
        } catch (Exception e) {
            return ResponseHelper.error("An error occurred while retrieving templates", 500);
        }
    }

    public ApiResponse<TemplateDTO> getTemplateById(Integer templateId) {
        try {
            Template template = templateRepository.findById(templateId)
                    .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + templateId));
            return ResponseHelper.success(TemplateDTO.fromEntity(template), "Template retrieved successfully");
        } catch (Exception e) {
            return ResponseHelper.error("An error occurred while retrieving template", 500);
        }
    }
}
