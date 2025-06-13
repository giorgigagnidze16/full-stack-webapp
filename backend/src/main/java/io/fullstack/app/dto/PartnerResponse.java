package io.fullstack.app.dto;

/**
 * DTO representing partner information
 *
 * @param company partner
 * @param country of partners origin
 * @param number of partner company
 * @param website of partner
 */
public record PartnerResponse(
    Integer id,
    String region,
    String company,
    String country,
    String number,
    String website,
    String imgUrl
) {
}
