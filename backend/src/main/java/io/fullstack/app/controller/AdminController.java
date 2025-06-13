package io.fullstack.app.controller;

import io.fullstack.app.converter.PartnerRequestConverter;
import io.fullstack.app.dto.CreatePartnerRequest;
import io.fullstack.app.dto.PartnerApplicationResponse;
import io.fullstack.app.dto.PartnerResponse;
import io.fullstack.app.dto.PartnerUpdateRequest;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.Region;
import io.fullstack.app.exception.NotFoundException;
import io.fullstack.app.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.fullstack.app.converter.PartnerRequestConverter.toPartnerResponse;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/partner-applications")
    public List<PartnerApplicationResponse> getPendingPartnerApplications() {
        return adminService.getPendingPartnerRequests();
    }

    @PostMapping("/partner-applications/{id}/approve")
    public ResponseEntity<?> approveApplication(
        @PathVariable Integer id,
        @RequestParam String website,
        @RequestParam String region,
        @RequestParam String img) {
        try {
            adminService.approvePartnerRequest(id, website, region, img);
            return ResponseEntity.ok("Application approved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/partner-applications/{id}/reject")
    public ResponseEntity<?> rejectApplication(@PathVariable Integer id) {
        try {
            adminService.rejectPartnerRequest(id);
            return ResponseEntity.ok("Application rejected");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/partners")
    public List<PartnerResponse> getPartners() {
        return adminService.getAllPartners().stream()
            .map(PartnerRequestConverter::toPartnerResponse)
            .toList();
    }

    @PostMapping("/partners")
    public ResponseEntity<PartnerResponse> addPartner(@RequestBody CreatePartnerRequest partner) {
        Partner saved = adminService.addPartner(mapToEntity(partner));
        return ResponseEntity.ok(toPartnerResponse(saved));
    }

    @PutMapping("/partners/{id}")
    public ResponseEntity<?> updatePartner(@PathVariable Integer id, @RequestBody PartnerUpdateRequest dto) {
        try {
            Partner updated = adminService.updatePartner(id, dto);
            return ResponseEntity.ok(toPartnerResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/partners/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Integer id) {
        try {
            adminService.deletePartner(id);
            return ResponseEntity.ok("Partner deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Partner mapToEntity(CreatePartnerRequest dto) {
        Region region = adminService.findRegionByName(dto.regionName())
            .orElseThrow(() -> new NotFoundException("Region not found"));

        return Partner.builder()
            .company(dto.company())
            .country(dto.country())
            .number(dto.number())
            .website(dto.website())
            .imgUrl(dto.img())
            .region(region)
            .build();
    }
}
