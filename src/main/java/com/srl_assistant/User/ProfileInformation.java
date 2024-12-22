package com.srl_assistant.User;


import com.srl_assistant.Company.Company;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileInformation {
    private String IDNO;
    private String registrationCertificate;
    private String companyName;
    private String businessAddress;
    private String iban;
    private String email;
    private String phoneNumber;

    //optional
    private String vatNumber;
    private String nameReservation;
    private Integer authorizedCapital;


    public static ProfileInformation fromEntity(Company company){
        return ProfileInformation.builder()
            .IDNO(company.getIDNO())
            .registrationCertificate(company.getRegistrationCertificate())
            .companyName(company.getCompanyName())
            .businessAddress(company.getBusinessAddress())
            .iban(company.getIban())
            .email(company.getEmail())
            .phoneNumber(company.getPhoneNumber())
            .vatNumber(company.getVatNumber())
            .nameReservation(company.getNameReservation())
            .authorizedCapital(company.getAuthorizedCapital())
            .build();
    }
}
