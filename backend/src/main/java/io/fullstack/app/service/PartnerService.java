package io.fullstack.app.service;


import io.fullstack.app.dto.PartnershipRequestDTO;
import io.fullstack.app.entity.PartnerRequest;
import io.fullstack.app.exception.AlreadyExistsException;
import io.fullstack.app.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRequestRepository repository;

    /**
     * Processes and saves partnership request in the database.
     *
     * @param request partnership request dto.
     * @throws AlreadyExistsException if request already exists either from the company or the same email.
     */
    public void processPartnershipRequest(PartnershipRequestDTO request) {
        if (repository.existsByEmailOrCompany(request.email(), request.company())) {
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

        repository.save(partnerRequest);

        log.info("Successfully saved partnership request from {}, {}", request.email(), request.company());
    }
}
