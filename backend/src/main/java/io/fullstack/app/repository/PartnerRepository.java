package io.fullstack.app.repository;


import io.fullstack.app.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}
