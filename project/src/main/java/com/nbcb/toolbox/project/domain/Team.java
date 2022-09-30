package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Team
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
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 32)
    private String dept;
}
