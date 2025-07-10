package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rating_seq")
    @SequenceGenerator(name = "rating_seq", sequenceName = "rating_seq", allocationSize = 1)
    @Column(name = "Id")
    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    @Column(name = "orderNumber")
    private Integer order;
}
