package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Process;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProcessRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/22 16:10
 * @Version 1.0
 **/
public interface ProcessRepository extends JpaRepository<Process, Integer> {
}
