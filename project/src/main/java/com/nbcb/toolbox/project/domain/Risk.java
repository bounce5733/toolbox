package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 风险
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
@Table(name = "risk")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subproject_id")
    private Integer subProjectId;

    @Column(name = "responsible_personnel_id")
    private Integer responsiblePersonnelId;

    private String measure;

    @Column(length = 32)
    private String type;

    private String desc;
}
