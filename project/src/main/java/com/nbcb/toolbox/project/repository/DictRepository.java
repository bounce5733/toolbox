package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DictRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface DictRepository extends JpaRepository<Dict, Integer> {
}
