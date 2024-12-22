package com.srl_assistant.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class DocumentDTO {

    private int id;
    private String linkMinio;
    private String name;
    private String tagType;
    private Integer score;
    private int userId;


    public static DocumentDTO fromEntity(Document document){
        return DocumentDTO.builder()
                .id(document.getId())
                .linkMinio(document.getLinkMinio())
                .name(document.getName())
                .tagType(document.getTag().getType())
                .score(document.getScore())
                .userId(document.getUser().getId())
                .build();
    }
}
