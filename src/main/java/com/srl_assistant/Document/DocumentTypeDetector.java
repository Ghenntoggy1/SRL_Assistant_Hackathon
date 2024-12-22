package com.srl_assistant.Document;

import com.srl_assistant.Tag.Tag;
import com.srl_assistant.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentTypeDetector {

    @Autowired
    private TagRepository tagRepository;

    public String detectDocumentType(String documentText) {
        List<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags) {
            String[] keywords = tag.getType().split(",");
            for (String keyword : keywords) {
                if (documentText.toLowerCase().contains(keyword.trim().toLowerCase())) {
                    return tag.getType();
                }
            }
        }
        return "Unknown Document Type";
    }
}
