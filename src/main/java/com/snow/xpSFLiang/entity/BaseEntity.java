package com.snow.xpSFLiang.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
    private Long id;

    @Column(name = "createdby_id")
    private Long createdBy_id;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updatedby_id")
    private Long updatedBy_id;
    @Column(name = "updated_date")
    private Date updatedDate;

}
