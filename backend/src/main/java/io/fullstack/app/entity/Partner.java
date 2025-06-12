package io.fullstack.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
