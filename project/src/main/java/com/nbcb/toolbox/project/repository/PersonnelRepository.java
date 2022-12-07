package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * PersonnelRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {

    @Query(value = "SELECT p.code, p.name, p.company, p.level, p.type, p.phone," +
            " p.position, p.charge, p.status, p.start_date, p.end_date, p.team_id, " +
            " t.dept dept, t.name team_name" +
            " FROM personnel p JOIN team t ON p.team_id = t.id" +
            " WHERE p.id NOT IN (SELECT r.personnel_id FROM resource r)" +
            " AND (p.name LIKE CONCAT('%', :name, '%') OR :name IS NULL)" +
            " AND (p.position = :position OR :position IS NULL)" +
            " AND (p.company = :company OR :company IS NULL) " +
            " AND (t.dept = :dept OR :dept IS NULL)",
            countQuery = "SELECT count(*) FROM personnel p JOIN team t ON p.team_id = t.id" +
                    " WHERE p.id NOT IN (SELECT r.personnel_id FROM resource r)" +
                    " AND (p.name LIKE CONCAT('%', :name, '%') OR :name IS NULL)" +
                    " AND (p.position = :position OR :position IS NULL)" +
                    " AND (p.company = :company OR :company IS NULL) " +
                    " AND (t.dept = :dept OR :dept IS NULL)",
            nativeQuery = true)
    Page<Map<String, Object>> pageFindIdleByCustomParams(@Param("name") String name, @Param("position") String position,
                                                         @Param("company") String company, @Param("dept") String dept,
                                                         Pageable pageable);
}
