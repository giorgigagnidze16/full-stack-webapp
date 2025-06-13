package io.fullstack.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartnerApplicationRequest(
    @NotBlank String firstname,
    @NotBlank String lastname,
    @NotBlank String company,
    @Email @NotBlank String email,
    @NotBlank String country,
    @NotBlank String phone,
    String message,
    @NotNull Boolean consent
) {
}
