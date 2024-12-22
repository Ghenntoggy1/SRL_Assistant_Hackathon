package com.srl_assistant.Tag;

import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("/tags")
    public ResponseEntity<ApiResponse<List<TagDTO>>> getAllTags() {
        try {
            List<Tag> tags = tagService.getAllTags();
            List<TagDTO> tagDTOs = tags.stream()
                    .map(TagDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseHelper.success(tagDTOs, "Tags retrieved successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.error("Error retrieving tags: " + e.getMessage(), 500));
        }
    }
}

