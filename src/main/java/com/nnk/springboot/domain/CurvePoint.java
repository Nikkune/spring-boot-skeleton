package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;


@Entity
@Table(name = "CurvePoint")
@Data
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "curve_point_seq")
    @SequenceGenerator(name = "curve_point_seq", sequenceName = "curve_point_seq", allocationSize = 1)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "CurveId")
    @NotNull
    private Integer curveId;
    private Timestamp asOfDate;
    @Positive
    private Double term;
    @Positive
    private Double value;
    private Timestamp creationDate;
}
