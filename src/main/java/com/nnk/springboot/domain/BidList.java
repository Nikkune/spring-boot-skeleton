package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "BidList")
@Data
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bid_list_seq")
    @SequenceGenerator(name = "bid_list_seq", sequenceName = "bid_list_seq", allocationSize = 1)
    private Integer BidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @Positive
    private Double bidQuantity;
    @Positive
    private Double askQuantity;
    @Positive
    private Double bid;
    @Positive
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
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
