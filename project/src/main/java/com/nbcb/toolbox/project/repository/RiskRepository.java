package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RiskRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface RiskRepository extends JpaRepository<Risk, Integer> {
}
