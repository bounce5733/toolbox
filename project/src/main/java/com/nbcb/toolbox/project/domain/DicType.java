package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DicType
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:58
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dict_type")
public class DicType {

    @Id
    @Column(length = 32)
    private String code;

    private String name;
}
