package io.fullstack.app.converter;

import io.fullstack.app.dto.PartnerApplicationResponse;
import io.fullstack.app.dto.PartnerResponse;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.PartnerRequest;
import io.fullstack.app.entity.Region;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PartnerRequestConverterTest {

    @Test
    void toPartnerApplicationResponse_shouldMapAllFields() {
        PartnerRequest pr = PartnerRequest.builder()
            .id(42)
            .firstname("Alice")
            .lastname("Smith")
            .company("Acme")
            .email("alice@acme.com")
            .country("nani")
            .phone("222333")
            .message("Please approve me pleaseeee !!!")
            .consent(true)
            .approved(false)
            .build();

        PartnerApplicationResponse response = PartnerRequestConverter.toPartnerApplicationResponse(pr);

        assertEquals(42, response.id());
        assertEquals("Alice", response.firstname());
        assertEquals("Smith", response.lastname());
        assertEquals("Acme", response.company());
        assertEquals("alice@acme.com", response.email());
        assertEquals("nani", response.country());
        assertEquals("222333", response.phone());
        assertEquals("Please approve me pleaseeee !!!", response.message());
        assertTrue(response.consent());
        assertFalse(response.approved());
    }

    @Test
    void toPartnerResponse_shouldMapAllFieldsIncludingRegion() {
        Region region = new Region(1, "EMEA");

        Partner partner = Partner.builder()
            .id(101)
            .region(region)
            .company("globalex")
            .country("amser")
            .number("+123456789")
            .website("https://maraky.com")
            .imgUrl("https://maraky.com/logo.png")
            .build();

        PartnerResponse response = PartnerRequestConverter.toPartnerResponse(partner);

        assertEquals(101, response.id());
        assertEquals("EMEA", response.region());
        assertEquals("globalex", response.company());
        assertEquals("amser", response.country());
        assertEquals("+123456789", response.number());
        assertEquals("https://maraky.com", response.website());
        assertEquals("https://maraky.com/logo.png", response.img());
    }

    @Test
    void toPartnerResponse_shouldHandleNullRegion() {
        Partner partner = Partner.builder()
            .id(202)
            .region(null)
            .company("turu")
            .country("idk")
            .number("000")
            .website("http://testigo.io")
            .imgUrl("http://testigo.io/logo.png")
            .build();

        PartnerResponse response = PartnerRequestConverter.toPartnerResponse(partner);

        assertEquals(202, response.id());
        assertNull(response.region());
        assertEquals("turu", response.company());
        assertEquals("idk", response.country());
        assertEquals("000", response.number());
        assertEquals("http://testigo.io", response.website());
        assertEquals("http://testigo.io/logo.png", response.img());
    }
}