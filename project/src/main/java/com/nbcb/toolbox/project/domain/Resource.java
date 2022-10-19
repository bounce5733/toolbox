package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 项目资源
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
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "personnel_id")
    private Integer personnelId;

    @Column(name = "current_ration")
    private Double currentRation;

    @Column(name = "next_project_id")
    private Integer nextProjectId;

    @Column(name = "prev_project_id")
    private Integer prevProjectId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "subproject_id")
    private Integer subProjectId;

}
