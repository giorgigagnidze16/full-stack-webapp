package io.fullstack.app.converter;

import io.fullstack.app.dto.PartnerApplicationResponse;
import io.fullstack.app.dto.PartnerResponse;
import io.fullstack.app.entity.Partner;
import io.fullstack.app.entity.PartnerRequest;

public class PartnerRequestConverter {
    public static PartnerApplicationResponse toPartnerApplicationResponse(PartnerRequest pr) {
        return new PartnerApplicationResponse(
            pr.getId(),
            pr.getFirstname(),
            pr.getLastname(),
            pr.getCompany(),
            pr.getEmail(),
            pr.getCountry(),
            pr.getPhone(),
            pr.getMessage(),
            pr.getConsent(),
            pr.getApproved()
        );
    }

    public static PartnerResponse toPartnerResponse(Partner partner) {
        return new PartnerResponse(
            partner.getId(),
            partner.getRegion() != null ? partner.getRegion().getName() : null,
            partner.getCompany(),
            partner.getCountry(),
            partner.getNumber(),
            partner.getWebsite(),
            partner.getImgUrl()
        );
    }
}
