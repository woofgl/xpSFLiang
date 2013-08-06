package com.snow.xpSFLiang.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_label")
@javax.persistence.SequenceGenerator(name = "SEQ_STORE", allocationSize = 1, sequenceName = "app_label_id_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class Label extends BaseEntity {
    private String label;

    public Label(){
    }
    public Label(String label){
        this.label = label;
    }



}
