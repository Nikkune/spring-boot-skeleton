package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "Trade")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "trade_seq")
    @SequenceGenerator(name = "trade_seq", sequenceName = "trade_seq", allocationSize = 1)
    @Column(name = "TradeId")
    private Integer tradeId;
    private String account;
    private String type;
    @Positive
    private Double buyQuantity;
    @Positive
    private Double sellQuantity;
    @Positive
    private Double buyPrice;
    @Positive
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
