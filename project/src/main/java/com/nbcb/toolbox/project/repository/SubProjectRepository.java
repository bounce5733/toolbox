package com.nbcb.toolbox.project.repository;

import com.nbcb.toolbox.project.domain.SubProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * SubProjectRepository
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:10
 * @Version 1.0
 **/
public interface SubProjectRepository extends JpaRepository<SubProject, Integer> {

    @Query(value = "SELECT sp.id, sp.system, sp.dept, sp.phase, sp.is_close, sp.pm, sp.pmo, sp.qa, sp.contract_id," +
            " c.name contract_name, c.type contract_type, c.start_date, c.end_date, c.company, c.status" +
            " FROM subproject sp JOIN project p ON sp.project_id = p.id" +
            " JOIN contract c ON sp.contract_id = c.id" +
            " WHERE (p.name LIKE CONCAT('%', :name, '%') OR :name IS NULL)" +
            " AND (sp.pm = :pm OR :pm IS NULL)" +
            " AND (sp.dept = :dept OR :dept IS NULL) AND (p.domain = :domain OR :domain IS NULL)" +
            " AND (sp.system = :system OR :system IS NULL) AND (sp.is_close = :isClose OR :isClose IS NULL)",
            countQuery = "SELECT count(*) FROM subproject sp JOIN project p ON sp.project_id = p.id" +
                    " JOIN contract c ON sp.contract_id = c.id" +
                    " WHERE (p.name LIKE CONCAT('%', :name, '%') OR :name IS NULL)" +
                    " AND (sp.dept = :dept OR :dept IS NULL) AND (p.domain = :domain OR :domain IS NULL)" +
                    " AND (sp.system = :system OR :system IS NULL) AND (sp.is_close = :isClose OR :isClose IS NULL)",
            nativeQuery = true)
    Page<Map<String, Object>> pageFindByCustomParams(@Param("name") String name, @Param("pm") String pm, @Param("dept") String dept,
                                                     @Param("domain") String domain, @Param("system") String system,
                                                     @Param("isClose") String isClose, Pageable pageable);
}
