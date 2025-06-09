package io.fullstack.app.controller;

import io.fullstack.app.dto.PricesQueryResponse;
import io.fullstack.app.service.PricingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PricingController {
    private final PricingPlanService service;

    @GetMapping
    public ResponseEntity<PricesQueryResponse> getPricingPlans() {
        return ResponseEntity.ok(new PricesQueryResponse(service.getAllActivePricingPlans()));
    }
}
