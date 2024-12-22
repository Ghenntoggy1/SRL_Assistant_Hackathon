package com.srl_assistant.File;


import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;


    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<?>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "user_id", required = false) Integer userId) {
        try {
            ApiResponse<?> response = fileService.uploadFileWithUser(file, userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    ResponseHelper.error(e.getMessage(), 400)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    ResponseHelper.error("An error occurred while uploading the file.", 500)
            );
        }
    }
}
