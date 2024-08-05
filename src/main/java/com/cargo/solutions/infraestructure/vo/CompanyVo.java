package com.cargo.solutions.infraestructure.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVo {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Identifier is required")
    private String identifier;

    @NotBlank(message = "Phone number is required")
    @Size(min = 7, max = 10, message = "The phone number must have a minimum of 7 and a maximum of 10 digits.")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "High street address is required")
    private String highStreet;

    @NotBlank(message = "Side street address is required")
    private String sideStreet;

    private Long identityTypeId;

    @NotBlank(message = "Identity is required")
    private String identity;
}
