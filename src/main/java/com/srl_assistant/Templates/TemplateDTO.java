package com.srl_assistant.Templates;


import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TemplateDTO {
    private int id;
    private String name;
    private String url;
    private String description;
    private String tagType;



    public static TemplateDTO fromEntity(Template template){
        return TemplateDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .url(template.getUrl())
                .description(template.getDescription())
                .tagType(template.getTag().getType())
                .build();
    }
}
