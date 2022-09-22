package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            " p.name project_name, dd.name domain, tmd.name team, r.end_date, r.next_subproject_id FROM resource r" +
            " JOIN subproject sp ON r.subproject_id = sp.id" +
            " JOIN personnel s ON r.personnel_id = s.id" +
            " JOIN dict td ON s.type = td.code AND td.type = 'PERSONNEL_TYPE'" +
            " JOIN dict ld ON s.type = ld.code AND ld.type = 'PERSONNEL_LEVEL'" +
            " JOIN dict dd ON p.domain = dd.code AND dd.type = 'DOMAIN'" +
            " JOIN dict tmd ON s.team = tmd.code AND tmd.type = 'TEAM'" +
            " JOIN project p ON sp.project_id = p.id WHERE sp.is_close = '0'" +
            " AND (s.dept = :dept OR :dept IS NULL) AND (s.team = :team OR :team IS NULL)" +
            " AND (s.name LIKE CONCAT('%', :personnelName, '%') OR :personnelName IS NULL)" +
            " AND (r.end_date LIKE CONCAT(:endMonth, '%') OR :endMonth IS NULL)" +
            " AND (p.domain = :domain OR :domain IS NULL)" +
            " AND (CASE :hasNextSubproject WHEN 1 THEN r.next_subproject_id IS NOT NULL ELSE r.next_subproject_id" +
            " IS NULL END OR :hasNextSubproject IS NULL)",
            countQuery = "SELECT count(*) FROM resource r" +
                    " JOIN subproject sp ON r.subproject_id = sp.id" +
                    " JOIN personnel s ON r.personnel_id = s.id" +
                    " JOIN dict td ON s.type = td.code AND td.type = 'PERSONNEL_TYPE'" +
                    " JOIN dict ld ON s.type = ld.code AND ld.type = 'PERSONNEL_LEVEL'" +
                    " JOIN dict dd ON p.domain = dd.code AND dd.type = 'DOMAIN'" +
                    " JOIN dict tmd ON s.team = tmd.code AND tmd.type = 'TEAM'" +
                    " JOIN project p ON sp.project_id = p.id WHERE sp.is_close = '0'" +
                    " AND (s.dept = :dept OR :dept IS NULL) AND (s.team = :team OR :team IS NULL)" +
                    " AND (s.name LIKE CONCAT('%', :personnelName, '%') OR :personnelName IS NULL)" +
                    " AND (r.end_date LIKE CONCAT(:endMonth, '%') OR :endMonth IS NULL)" +
                    " AND (p.domain = :domain OR :domain IS NULL)" +
                    " AND (CASE :hasNextSubproject WHEN 1 THEN r.next_subproject_id IS NOT NULL ELSE r.next_subproject_id" +
                    " IS NULL END OR :hasNextSubproject IS NULL)",
            nativeQuery = true)
    Page<Map<String, Object>> findResourceByCustomParams(@Param("dept") String dept, @Param("team") String team,
                                                         @Param("personnelName") String personnelName, @Param("endMonth") String endMonth,
                                                         @Param("domain") String domain,
                                                         @Param("hasNextSubproject") Integer hasNextSubproject,
                                                         Pageable pageable);
}
