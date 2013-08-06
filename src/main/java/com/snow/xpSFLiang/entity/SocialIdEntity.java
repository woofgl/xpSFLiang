package com.snow.xpSFLiang.entity;


import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "app_social_id_entity")
@Data
@javax.persistence.SequenceGenerator(name = "SEQ_STORE", allocationSize = 1, sequenceName = "app_social_id_entity_id_seq")
public class SocialIdEntity extends BaseEntity {
    private Long   user_id;
    @Column(length = 2048)
    private String token;
    @Column(name="token_date")
    private Date tokenDate;
    private String service = "SaleForce";
    private String email;
    private String secret;
    @Transient
    private boolean isValid = false;
    

}
