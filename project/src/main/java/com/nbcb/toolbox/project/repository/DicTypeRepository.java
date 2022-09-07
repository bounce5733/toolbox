package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.DicType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DicTypeRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface DicTypeRepository extends JpaRepository<DicType, String> {
}
