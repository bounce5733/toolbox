package com.nbcb.toolbox.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 项目
 *
 * @Author jiangyonghua
 * @Date 2022/9/7 09:27
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 32)
    private String phase;

    @Column(length = 32)
    private String domain; // 板块

    @Column(name = "pm_id")
    private Integer pmId; // 项目经理

    @Column(name = "is_project_group", length = 1)
    private String isProjectGroup; // 是否项目组？

    private Double delay; // 延期？

    private String issue;

    @Column(name = "online_date", length = 10)
    private String onlineDate;


}
