package com.nbcb.toolbox.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * 子项目人员关系
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
@Table(name = "subproject_personnel")
public class SubProjectPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "personnel_id")
    private Integer personnelId;

    @Column(name = "current_ration")
    private Double currentRation;

    @Column(name = "next_subproject_id")
    private Integer nextSubprojectId;

    @Column(name = "prev_subproject_id")
    private Integer prevSubprojectId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "subproject_id")
    private Integer subProjectId;

}
