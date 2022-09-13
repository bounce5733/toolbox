package com.nbcb.toolbox.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subproject_id", referencedColumnName = "id")
    private Set<SubProjectPersonnel> subProjectPersonnels;

}
