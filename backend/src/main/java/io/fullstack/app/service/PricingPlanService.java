package io.fullstack.app.service;


import io.fullstack.app.entity.PricingPlan;
import io.fullstack.app.exception.NotFoundException;
import io.fullstack.app.model.CacheName;
import io.fullstack.app.repository.PricingPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PricingPlanService {
    private final PricingPlanRepository repository;

    @Cacheable(value = CacheName.ALL_PRICING_PLANS)
    public List<PricingPlan> getAllActivePricingPlans() {
        var pricingPlans = repository.findAll();

        if (pricingPlans.isEmpty()) {
            throw new NotFoundException("Pricing plans not found in the db");
        }

        log.debug("Retrieved {} number of pricing plans", pricingPlans.size());

        return pricingPlans;
    }
}
