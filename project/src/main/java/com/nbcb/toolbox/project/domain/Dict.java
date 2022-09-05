package com.nbcb.toolbox.project.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dict
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 15:20
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "Dict")
public class Dict {

    @Id
    private int id;

    @Column(length = 32)
    private String code;

    @Column(length = 255)
    private String name;

    @Column(length = 32)
    private String type;
}
