package tlu.web.doan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tlu.web.doan.models.enums.UserRole;

@Data
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String fullName;

    private String phoneNumber;

    private UserRole role = UserRole.CUSTOMER; // mặc định
}