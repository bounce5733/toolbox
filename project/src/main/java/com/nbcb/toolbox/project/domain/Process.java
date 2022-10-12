package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 子项进度
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
@Table(name = "process")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subproject_id")
    private Integer subProjectId;

    @Column(name = "invest_rate")
    private Double investRate; // 投入占比（%）

    @Column(length = 32)
    private String phase; // 阶段

    @Column(name = "plan_start_date", length = 10)
    private String planStartDate;

    @Column(name = "plan_end_date", length = 10)
    private String planEndDate;

    @Column(name = "really_start_date", length = 10)
    private String reallyStartDate;

    @Column(name = "really_end_date", length = 10)
    private String reallyEndDate;

    @Column(name = "plan_man_days")
    private Integer planManDays; // 计划工作量

    @Column(name = "current_process")
    private Integer currentProcess; // 当前进度（%）
}