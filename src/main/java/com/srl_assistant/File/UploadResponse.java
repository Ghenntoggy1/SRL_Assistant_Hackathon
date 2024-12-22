package com.srl_assistant.File;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class UploadResponse {
    private Integer fileId;
    private String minioUrl;
    private String fileType;
}
