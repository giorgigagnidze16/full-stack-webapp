package io.fullstack.app.dto;

public record PartnerUpdateRequest(
    String company,
    String country,
    String number,
    String website,
    String imgUrl
) {
}
