package io.fullstack.app.repository;

import io.fullstack.app.entity.PartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Integer> {
    @Query(
        value = """
            SELECT EXISTS(
              SELECT 1
                FROM partner_requests pr
               WHERE pr.email   = :email
                  OR pr.company = :company
            )
            """,
        nativeQuery = true)
    boolean existsByEmailOrCompany(String email, String company);
}
