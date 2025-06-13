package io.fullstack.app.service;

import io.fullstack.app.converter.PartnerRequestConverter;
import io.fullstack.app.dto.PartnerApplicationResponse;
import io.fullstack.app.dto.PartnerUpdateRequest;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.Region;
import io.fullstack.app.exception.NotFoundException;
import io.fullstack.app.repository.PartnerRepository;
import io.fullstack.app.repository.PartnerRequestRepository;
import io.fullstack.app.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final RegionRepository regionRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerRequestRepository partnerRequestRepository;

    public List<PartnerApplicationResponse> getPendingPartnerRequests() {
        return partnerRequestRepository.findByApprovedIsFalse().stream()
            .map(PartnerRequestConverter::toPartnerApplicationResponse)
            .toList();
    }

    public void approvePartnerRequest(Integer requestId, String website, String region, String imgUrl) {
        var regionOpt = regionRepository.findByName(region);

        if (regionOpt.isEmpty()) throw new NotFoundException("Region  not found");
        var requestOpt = partnerRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) throw new NotFoundException("Partner request not found");

        var request = requestOpt.get();
        if (request.getApproved())
            throw new IllegalStateException("Request already processed");

        Partner partner = Partner.builder()
            .company(request.getCompany())
            .country(request.getCountry())
            .number(request.getPhone())
            .website(website)
            .imgUrl(imgUrl)
            .region(regionOpt.get())
            .build();

        partnerRepository.save(partner);

        request.setApproved(true);
        partnerRequestRepository.save(request);
    }

    public void rejectPartnerRequest(Integer requestId) {
        var requestOpt = partnerRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) throw new NotFoundException("Partner request not found");

        var request = requestOpt.get();
        partnerRequestRepository.delete(request);
    }

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Integer id, PartnerUpdateRequest request) {
        var partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isEmpty()) throw new NotFoundException("Partner not found");

        Partner partner = partnerOpt.get();
        partner.setCompany(request.company());
        partner.setCountry(request.country());
        partner.setNumber(request.number());
        partner.setWebsite(request.website());
        partner.setImgUrl(request.imgUrl());

        return partnerRepository.save(partner);
    }

    public void deletePartner(Integer id) {
        if (!partnerRepository.existsById(id)) throw new NotFoundException("Partner not found");
        partnerRepository.deleteById(id);
    }

    public Optional<Region> findRegionByName(String regionName) {
        return regionRepository.findByName(regionName);
    }

}
