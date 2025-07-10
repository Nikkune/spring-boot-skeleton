package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RuleName")
@Data
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
