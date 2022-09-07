package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Contract;
import com.nbcb.toolbox.project.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ContractRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
