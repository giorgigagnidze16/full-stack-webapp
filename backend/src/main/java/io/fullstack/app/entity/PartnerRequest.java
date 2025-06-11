package io.fullstack.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partner_requests")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String company;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phone;

    private String message;

    @Column(nullable = false)
    private Boolean consent;
}
