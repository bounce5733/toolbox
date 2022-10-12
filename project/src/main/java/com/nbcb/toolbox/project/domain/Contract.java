package com.nbcb.toolbox.project.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 项目合同
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
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 32)
    private String company;

    @Column(length = 32)
    private String status;

    @Column(length = 32)
    private String type;

    @Column(name = "start_date", length = 10)
    private String startDate;

    @Column(name = "end_date", length = 10)
    private String endDate;
}
