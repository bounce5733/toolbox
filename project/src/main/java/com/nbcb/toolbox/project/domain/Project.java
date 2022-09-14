package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * 项目
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
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String code;

    private String name;

    @Column(length = 32)
    private String domain; // 业务条线

    private String issue;

    @Column(name = "online_date", length = 10)
    private String onlineDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Set<SubProject> subProjects;
}