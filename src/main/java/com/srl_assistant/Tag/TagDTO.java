package com.srl_assistant.Tag;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    private int id;
    private String type;


    public static TagDTO fromEntity(Tag tag){
        return TagDTO.builder()
                .id(tag.getId())
                .type(tag.getType())
                .build();
    }


}
