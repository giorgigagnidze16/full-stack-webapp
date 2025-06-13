package io.fullstack.app.dto;

public record PartnerApplicationResponse(
    Integer id,
    String firstname,
    String lastname,
    String company,
    String email,
    String country,
    String phone,
    String message,
    Boolean consent,
    Boolean approved
) {
}
