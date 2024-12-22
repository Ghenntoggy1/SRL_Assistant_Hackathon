package com.srl_assistant.Company;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CompanyDTO {
    private int id;
    private String IDNO;
    private String registrationCertificate;
    private String companyName;
    private String businessAddress;
    private String iban;
    private String email;
    private String phoneNumber;
    private String vatNumber;
    private String nameReservation;
    private Integer authorizedCapital;

    public static CompanyDTO fromEntity(Company company){
        return CompanyDTO.builder()
                .id(company.getId())
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
