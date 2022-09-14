package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 项目人员
 *
 * @Author jiangyonghua
 * @Date 2022/9/7 09:27
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personnel")
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String code;

    private String name;

    @Column(length = 32)
    private String company;

    @Column(length = 32)
    private String level;

    @Column(length = 32)
    private String type;

    private String phone;

    @Column(length = 32)
    private String team;

    @Column(length = 32)
    private String dept;

    @Column(length = 32)
    private String position;

    private Integer charge;

    @Column(length = 32)
    private String status;

    @Column(length = 10)
    private String startDate;

    @Column(length = 10)
    private String endDate;
}
