package com.srl_assistant.Company;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.srl_assistant.User.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

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

}
