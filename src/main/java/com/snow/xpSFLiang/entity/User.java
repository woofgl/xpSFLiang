package com.snow.xpSFLiang.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
@javax.persistence.SequenceGenerator(name = "SEQ_STORE", allocationSize = 1, sequenceName = "app_user_id_seq")
@Data
public class User extends BaseEntity {
    
    private String username;
    private String password;

}
