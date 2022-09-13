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

    private String name;

    @Column(length = 32)
    private String company;

    @Column(length = 32)
    private String level;

    @Column(length = 32)
    private String type;

    @Column(name = "is_pm", length = 1)
    private String isPm;

    @Column(name = "is_admin", length = 1)
    private String isAdmin;

    private String phone;
}
