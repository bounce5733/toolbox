package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.SubProjectPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 子项人员数据操作
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface SubProjectPersonnelRepository extends JpaRepository<SubProjectPersonnel, Integer> {
}
