package com.snow.xpSFLiang.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_label_rel")
@javax.persistence.SequenceGenerator(name = "SEQ_STORE", allocationSize = 1, sequenceName = "app_lable_rel_id_seq")
@Data
public class LabelRel extends BaseEntity {
    private String contactId;
    @OneToOne
    private Label label;
}
