package io.fullstack.app.controller;

import io.fullstack.app.dto.PartnerResponse;
import io.fullstack.app.dto.PartnerApplicationRequest;
import io.fullstack.app.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping
    public ResponseEntity<String> registerPartner(@Valid @RequestBody PartnerApplicationRequest request) {
        log.info("Received a partner registration request {}", request);
        partnerService.processPartnershipRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> findAll() {
        return ResponseEntity.ok(partnerService.findAll());
    }
}
