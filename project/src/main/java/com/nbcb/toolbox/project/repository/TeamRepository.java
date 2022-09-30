package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TeamRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
