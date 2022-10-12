package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 子项目
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
@Table(name = "subproject")
public class SubProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String system;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(length = 32)
    private String phase;

    @Column(length = 32)
    private String dept;

    private Integer pm;

    private Integer pmo;

    private Integer qa;

    @Column(name = "is_close", length = 1)
    private String isClose = "0";

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subproject_id", referencedColumnName = "id")
    private List<Process> processes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subproject_id", referencedColumnName = "id")
    private List<Resource> resources;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subproject_id", referencedColumnName = "id")
    private List<Risk> risks;

}
