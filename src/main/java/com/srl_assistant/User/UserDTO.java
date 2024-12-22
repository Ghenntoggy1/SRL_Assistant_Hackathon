package com.srl_assistant.User;


import com.srl_assistant.Company.Company;
import com.srl_assistant.Company.CompanyDTO;
import com.srl_assistant.Document.DocumentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;
    private String username;
    private String email;
    private CompanyDTO company;
    private List<DocumentDTO> documents;


    public static UserDTO fromEntity(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .company(CompanyDTO.fromEntity(user.getCompany()))
                .documents(user.getDocuments().stream().map(DocumentDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }

}
