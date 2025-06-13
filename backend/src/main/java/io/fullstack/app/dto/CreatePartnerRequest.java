package io.fullstack.app.dto;

public record CreatePartnerRequest(
    String company,
    String country,
    String number,
    String website,
    String img,
    String regionName
) {
}
