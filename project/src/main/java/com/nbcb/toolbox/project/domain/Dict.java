package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Dict
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 15:20
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dict")
public class Dict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String code;

    private String name;

    @Column(length = 32)
    private String type;
}
