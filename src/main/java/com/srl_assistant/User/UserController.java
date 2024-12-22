package com.srl_assistant.User;

import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest loginRequest) {
        try {
            ApiResponse<UserDTO> response = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .message(e.getMessage())
                    .errorCode(400)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.builder()
                    .message("Internal server error")
                    .errorCode(500)
                    .build());
        }
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<String>> updateUserProfile(
            @PathVariable("userId") Integer userId,
            @RequestBody ProfileInformation profileInfo) {
        try {
            userService.updateUserProfile(userId, profileInfo);

            return ResponseEntity.ok(ResponseHelper.success("Profile updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.error("Error updating profile: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<ProfileInformation>> getUserProfile(@PathVariable("userId") Integer userId) {
        try {
            ProfileInformation user = userService.getUserProfile(userId);
            return ResponseEntity.ok(ResponseHelper.success(user, "User profile retrieved successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ResponseHelper.error("Error retrieving user profile: " + e.getMessage(), 500));
        }
    }

}
