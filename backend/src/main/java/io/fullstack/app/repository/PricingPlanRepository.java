package io.fullstack.app.repository;

import io.fullstack.app.entity.PricingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Integer> {
}
