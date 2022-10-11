package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.ResourceHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * ResourceHisRepository
 *
 * @author: yh.jiang
 * @time: 2022/10/9 09:57
 */
public interface ResourceHisRepository extends JpaRepository<ResourceHis, Integer> {

    @Query(value = "SELECT sp.dept, rs.personnel_id, rs.start_date, rs.end_date, p.domain, s.type, s.team_id FROM resource_his rs" +
            " LEFT JOIN subproject sp ON rs.subproject_id = sp.id LEFT JOIN personnel s ON rs.personnel_id = s.id" +
            " LEFT JOIN project p ON sp.project_id = p.id" +
            " WHERE (sp.dept = :dept OR :dept IS NULL) AND ((rs.start_date <= :endDate OR :endDate IS NULL)" +
            " AND (rs.end_date >= :startDate OR :startDate IS NULL))" +
            " AND (p.domain in :domains OR :domains IS NULL)" +
            " AND (s.team_id in :teams OR :teams IS NULL)", nativeQuery = true)
    List<Map<String, Object>> findByCustomParams(@Param("dept") String dept, @Param("startDate") String startDate,
                                                 @Param("endDate") String endDate,
                                                 @Param("domains") List<String> domains,
                                                 @Param("teams") List<Integer> teams);
}
