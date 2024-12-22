package com.srl_assistant.User;

import com.srl_assistant.Company.Company;
import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse<UserDTO> login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password are required.");
        }

        User userEntity = userRepository.findByEmail(email);
        UserDTO user = UserDTO.fromEntity(userEntity);


        if (userEntity == null || !userEntity.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return ResponseHelper.success(user, "Login successful.");
    }

    public void updateUserProfile(Integer userId, ProfileInformation profileInfo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (profileInfo.getEmail() != null) {
            user.setEmail(profileInfo.getEmail());
        }

        Company company = user.getCompany();
        if (company == null) {
            company = new Company();
            company.setUser(user);
        }

        if (profileInfo.getCompanyName() != null) {
            company.setCompanyName(profileInfo.getCompanyName());
        }
        if (profileInfo.getBusinessAddress() != null) {
            company.setBusinessAddress(profileInfo.getBusinessAddress());
        }
        if (profileInfo.getRegistrationCertificate() != null) {
            company.setRegistrationCertificate(profileInfo.getRegistrationCertificate());
        }
        if (profileInfo.getIban() != null) {
            company.setIban(profileInfo.getIban());
        }
        if (profileInfo.getVatNumber() != null) {
            company.setVatNumber(profileInfo.getVatNumber());
        }
        if (profileInfo.getNameReservation() != null) {
            company.setNameReservation(profileInfo.getNameReservation());
        }
        if (profileInfo.getAuthorizedCapital() != null) {
            company.setAuthorizedCapital(profileInfo.getAuthorizedCapital());
        }
        user.setCompany(company);
        userRepository.save(user);
    }

    public ProfileInformation getUserProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Company company = user.getCompany();
        if (company == null) {
            return ProfileInformation.builder().build();
        }

        return ProfileInformation.fromEntity(company);
    }
}
