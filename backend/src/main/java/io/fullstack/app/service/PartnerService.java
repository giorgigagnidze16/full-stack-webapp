package io.fullstack.app.service;


import io.fullstack.app.dto.PartnerResponse;
import io.fullstack.app.dto.PartnerApplicationRequest;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.PartnerRequest;
import io.fullstack.app.exception.AlreadyExistsException;
import io.fullstack.app.repository.PartnerRepository;
import io.fullstack.app.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerRequestRepository partnerRequestRepository;

    /**
     * Processes and saves partnership request in the database.
     *
     * @param request partnership request dto.
     * @throws AlreadyExistsException if request already exists either from the company or the same email.
     */
    public void processPartnershipRequest(PartnerApplicationRequest request) {
        if (partnerRequestRepository.existsByEmailOrCompany(request.email(), request.company())) {
            throw new AlreadyExistsException("Partnership request already exists from company: " + request.company());
        }

        var partnerRequest = PartnerRequest.builder()
            .firstname(request.firstname())
            .lastname(request.lastname())
            .company(request.company())
            .consent(request.consent())
            .country(request.country())
            .email(request.email())
            .message(request.message())
            .phone(request.phone())
            .build();

        partnerRequestRepository.save(partnerRequest);

        log.info("Successfully saved partnership request from {}, {}", request.email(), request.company());
    }

    public List<PartnerResponse> findAll() {
        log.info("Retrieving partners from the db.");
        return partnerRepository.findAll()
            .stream()
            .map(PartnerService::mapPartnerEntityToPartnerResponse)
            .toList();
    }

    private static PartnerResponse mapPartnerEntityToPartnerResponse(Partner p) {
        return new PartnerResponse(p.getId(), p.getRegion().getName(), p.getCompany(), p.getCountry(), p.getNumber(), p.getWebsite(), p.getImgUrl());
    }
}
