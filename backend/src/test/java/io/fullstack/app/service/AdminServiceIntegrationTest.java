package io.fullstack.app.service;

import io.fullstack.app.dto.PartnerUpdateRequest;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.PartnerRequest;
import io.fullstack.app.entity.Region;
import io.fullstack.app.repository.PartnerRepository;
import io.fullstack.app.repository.PartnerRequestRepository;
import io.fullstack.app.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Rollback
@Transactional
@SpringBootTest
class AdminServiceIntegrationTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerRequestRepository partnerRequestRepository;

    @Test
    void testApprovePartnerRequestFlow() {
        PartnerRequest request = PartnerRequest.builder()
            .firstname("John")
            .lastname("Doe")
            .company("Test Corp")
            .country("Bakurian with selenium")
            .phone("123456789")
            .email("john.doe@test.com")
            .consent(true)
            .approved(false)
            .build();

        partnerRequestRepository.save(request);

        adminService.approvePartnerRequest(request.getId(), "https://test.com", "EMEA", "img.png");

        PartnerRequest updatedRequest = partnerRequestRepository.findById(request.getId()).orElseThrow();
        assertTrue(updatedRequest.getApproved());

        List<Partner> partners = partnerRepository.findAll();
        assertEquals(6, partners.size());
        Partner partner = partners.getLast();
        assertEquals("Test Corp", partner.getCompany());
        assertEquals("https://test.com", partner.getWebsite());
        assertEquals("img.png", partner.getImgUrl());
    }

    @Test
    void testRejectPartnerRequest() {
        PartnerRequest request = PartnerRequest.builder()
            .firstname("John")
            .lastname("Doe")
            .company("Test Corp")
            .country("Bakurian")
            .email("meow.doe@test.com")
            .company("Reject Corp")
            .country("cavatanem")
            .phone("987654321")
            .consent(true)
            .approved(false)
            .build();
        partnerRequestRepository.save(request);

        adminService.rejectPartnerRequest(request.getId());

        assertFalse(partnerRequestRepository.findById(request.getId()).isPresent());
    }

    @Test
    void testAddUpdateDeletePartner() {
        Region region = regionRepository.save(new Region(null, "Asia"));
        Partner partner = Partner.builder()
            .company("Initial Corp")
            .country("Terjolski")
            .number("111222333")
            .website("http://initial.com")
            .imgUrl("init.png")
            .region(region)
            .build();

        Partner savedPartner = adminService.addPartner(partner);
        assertNotNull(savedPartner.getId());

        PartnerUpdateRequest dto = new PartnerUpdateRequest(
            "Updated Corp", "Terjolski", "999888777", "http://updated.com", "updated.png"
        );
        Partner updatedPartner = adminService.updatePartner(savedPartner.getId(), dto);

        assertEquals("Updated Corp", updatedPartner.getCompany());
        assertEquals("Terjolski", updatedPartner.getCountry());

        adminService.deletePartner(savedPartner.getId());
        assertFalse(partnerRepository.existsById(savedPartner.getId()));
    }
}
