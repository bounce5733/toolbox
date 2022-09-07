package com.nbcb.toolbox.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * DicType
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:58
 * @Version 1.0
 **/
@Data
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
