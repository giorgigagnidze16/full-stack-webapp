package io.fullstack.app.dto;

import io.fullstack.app.entity.PricingPlan;

import java.util.List;

public record PricesQueryResponse(List<PricingPlan> prices) {
}
