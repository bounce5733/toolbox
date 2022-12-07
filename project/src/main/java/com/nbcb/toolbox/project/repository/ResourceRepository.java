package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * ResourceRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    @Query(value = "SELECT s.name personnel_name, td.name personnel_type, ld.name personnel_level," +
            " p.name project_name, dd.name domain, dept.name dept, t.name team, r.end_date," +
            " r.next_project_id FROM resource r" +
            " LEFT JOIN subproject sp ON r.subproject_id = sp.id" +
            " LEFT JOIN personnel s ON r.personnel_id = s.id" +
            " LEFT JOIN team t ON s.team_id = t.id" +
            " LEFT JOIN dict td ON s.type = td.code AND td.type = 'PERSONNEL_TYPE'" +
            " LEFT JOIN dict ld ON s.level = ld.code AND ld.type = 'PERSONNEL_LEVEL'" +
            " LEFT JOIN dict dept ON t.dept = dept.code AND dept.type = 'DEPT'" +
            " LEFT JOIN project p ON sp.project_id = p.id " +
            " LEFT JOIN dict dd ON p.domain = dd.code AND dd.type = 'DOMAIN'" +
            " WHERE sp.is_close = '0'" +
            " AND (dept.code = :dept OR :dept IS NULL) AND (t.id = :team OR :team IS NULL)" +
            " AND (s.name LIKE CONCAT('%', :personnelName, '%') OR :personnelName IS NULL)" +
            " AND (s.type = :personnelType OR :personnelType IS NULL)" +
            " AND (s.level = :personnelLevel OR :personnelLevel IS NULL)" +
            " AND (r.end_date LIKE CONCAT(:endMonth, '%') OR :endMonth IS NULL)" +
            " AND (p.domain = :domain OR :domain IS NULL)" +
            " AND (CASE :hasNextProject WHEN 1 THEN r.next_project_id IS NOT NULL ELSE r.next_project_id" +
            " IS NULL END OR :hasNextProject IS NULL)",
            countQuery = "SELECT count(*) FROM resource r" +
                    " LEFT JOIN subproject sp ON r.subproject_id = sp.id" +
                    " LEFT JOIN personnel s ON r.personnel_id = s.id" +
                    " LEFT JOIN team t ON s.team_id = t.id" +
                    " LEFT JOIN dict td ON s.type = td.code AND td.type = 'PERSONNEL_TYPE'" +
                    " LEFT JOIN dict ld ON s.level = ld.code AND ld.type = 'PERSONNEL_LEVEL'" +
                    " LEFT JOIN dict dept ON t.dept = dept.code AND dept.type = 'DEPT'" +
                    " LEFT JOIN project p ON sp.project_id = p.id " +
                    " LEFT JOIN dict dd ON p.domain = dd.code AND dd.type = 'DOMAIN'" +
                    " WHERE sp.is_close = '0'" +
                    " AND (dept.code = :dept OR :dept IS NULL) AND (t.id = :team OR :team IS NULL)" +
                    " AND (s.name LIKE CONCAT('%', :personnelName, '%') OR :personnelName IS NULL)" +
                    " AND (s.type = :personnelType OR :personnelType IS NULL)" +
                    " AND (s.level = :personnelLevel OR :personnelLevel IS NULL)" +
                    " AND (r.end_date LIKE CONCAT(:endMonth, '%') OR :endMonth IS NULL)" +
                    " AND (p.domain = :domain OR :domain IS NULL)" +
                    " AND (CASE :hasNextProject WHEN 1 THEN r.next_project_id IS NOT NULL ELSE r.next_project_id" +
                    " IS NULL END OR :hasNextProject IS NULL)",
            nativeQuery = true)
    Page<Map<String, Object>> pageFindByCustomParams(@Param("dept") String dept, @Param("team") Integer team,
                                                     @Param("personnelName") String personnelName,
                                                     @Param("personnelType") String personnelType,
                                                     @Param("personnelLevel") String personnelLevel,
                                                     @Param("endMonth") String endMonth,
                                                     @Param("domain") String domain,
                                                     @Param("hasNextProject") Integer hasNextProject,
                                                     Pageable pageable);

    @Query(value = "SELECT p.domain, sp.dept, r.personnel_id, s.type, s.team_id, r.start_date, r.end_date" +
            " FROM resource r JOIN subproject sp ON r.subproject_id = sp.id JOIN project p ON sp.project_id = p.id" +
            " JOIN personnel s ON r.personnel_id = s.id" +
            " JOIN team t ON s.team_id = t.id" +
            " WHERE (t.dept = :dept OR :dept IS NULL) AND ((r.start_date <= :endDate OR :endDate IS NULL)" +
            " AND (r.end_date >= :startDate OR :startDate IS NULL))" +
            " AND (p.domain in :domains OR :domains IS NULL)" +
            " AND (s.team_id in :teams OR :teams IS NULL)", nativeQuery = true)
    List<Map<String, Object>> findByCustomParams(@Param("dept") String dept, @Param("startDate") String startDate,
                                                 @Param("endDate") String endDate,
                                                 @Param("domains") List<String> domains,
                                                 @Param("teams") List<Integer> teams);
}
