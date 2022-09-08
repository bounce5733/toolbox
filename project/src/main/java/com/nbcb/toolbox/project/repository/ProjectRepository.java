package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProjectRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
