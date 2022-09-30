package com.nbcb.toolbox.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ProjectService
 *
 * @Author jiangyonghua
 * @Date 2022/9/29 14:16
 * @Version 1.0
 **/
@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private Context context;

    public void exportExcel() throws JsonProcessingException {
        Map<Integer, Personnel> personnelMap = new HashMap<>();
        List<Personnel> personnelList = personnelRepository.findAll();
        personnelList.forEach(personnel -> {
            personnelMap.put(personnel.getId(), personnel);
        });
        Map<Integer, Project> projectMap = new HashMap<>(); // 子项目id->主项目映射
        List<Project> projectList = projectRepository.findAll();
        projectList.forEach(project -> {
            project.getSubProjects().forEach(subProject -> {
                projectMap.put(subProject.getId(), project);
            });
        });
        Set<String> systems = new HashSet<>();
        List<Map<String, Object>> projectRows = new ArrayList<>();
        projectList.forEach(project -> {
            Map<String, Object> projectRow = new HashMap<>();
            Map<String, String> baseinfo = new HashMap<>();
            baseinfo.put("name", project.getName());
            baseinfo.put("issue", project.getIssue());
            baseinfo.put("domain", project.getDomain());
            baseinfo.put("onlineDate", project.getOnlineDate());
            projectRow.put("baseinfo", baseinfo);
            Map<Integer, Object> subProjectRow = new HashMap<>();
            project.getSubProjects().forEach(subProject -> {
                if (subProject.getIsClose().equals("0")) {
                    systems.add(subProject.getSystem());
                    Integer resourceIndex = 0;
                    for (Resource resource : subProject.getResources()) {
                        Map<String, String> resourceMap = new HashMap<>();
                        Personnel personnel = personnelMap.get(resource.getPersonnelId());
                        resourceMap.put(subProject.getSystem() + "_" + "startDate", resource.getStartDate());
                        resourceMap.put(subProject.getSystem() + "_" + "prevSubproject", projectMap.get(resource.getPrevSubprojectId()) == null ? "/" :
                                projectMap.get(resource.getPrevSubprojectId()).getName());
                        resourceMap.put(subProject.getSystem() + "_" + "personnelName", personnel.getName());
                        resourceMap.put(subProject.getSystem() + "_" + "supplierName", context.getDictCache().get("SUPPLIER").get(personnel.getCompany())
                                == null ? "/" : context.getDictCache().get("SUPPLIER").get(personnel.getCompany()));
                        resourceMap.put(subProject.getSystem() + "_" + "personnelType", personnel.getType());
                        resourceMap.put(subProject.getSystem() + "_" + "personnelLevel", personnel == null ? "" : personnel.getLevel());
                        resourceMap.put(subProject.getSystem() + "_" + "currentRation", String.valueOf(resource.getCurrentRation()));
                        resourceMap.put(subProject.getSystem() + "_" + "nextSubproject", projectMap.get(resource.getNextSubprojectId()) == null ? "/" :
                                projectMap.get(resource.getNextSubprojectId()).getName());
                        resourceMap.put(subProject.getSystem() + "_" + "endDate", resource.getEndDate());
                        resourceMap.put(subProject.getSystem() + "_" + "contract", context.getDictCache().get("CONTRACT_STATUS")
                                .get(subProject.getContract().getStatus()) == null ? "/" : context.getDictCache().get("CONTRACT_STATUS")
                                .get(subProject.getContract().getStatus()));
                        subProjectRow.put(resourceIndex, resourceMap);
                        resourceIndex++;
                    }
                    projectRow.put("subprojects", subProjectRow);
                    projectRows.add(projectRow);
                }
            });
        });
        List<Integer> spanrowArr = new ArrayList<>();
        List<Map<String, String>> projects = new ArrayList<>();
        projectRows.forEach(projectRow -> {
            Map<Integer, Object> subProjectRow = (Map<Integer, Object>) projectRow.get("subprojects");
            subProjectRow.forEach((key, val) -> {
                Map<String, String> subrow = (Map<String, String>) val;
                Map<String, String> row = new HashMap<>();
                row.putAll((Map<String, String>) projectRow.get("baseinfo"));
                systems.forEach(system -> {
                    if (null == subrow.get(system + "_" + "startDate")) {
                        subrow.put(system + "_" + "startDate", "/");
                        subrow.put(system + "_" + "prevSubproject", "/");
                        subrow.put(system + "_" + "personnelName", "/");
                        subrow.put(system + "_" + "supplierName", "/");
                        subrow.put(system + "_" + "personnelType", "/");
                        subrow.put(system + "_" + "personnelLevel", "/");
                        subrow.put(system + "_" + "currentRation", "/");
                        subrow.put(system + "_" + "nextSubproject", "/");
                        subrow.put(system + "_" + "endDate", "/");
                        subrow.put(system + "_" + "contract", "/");
                    }
                });
                row.putAll(subrow);
                projects.add(row);
                spanrowArr.add(subProjectRow.size());
            });
        });
        log.info("projects:{}", new ObjectMapper().writeValueAsString(projects));
        // 计算跨行数据列
        List<Integer> rowspanCols = new ArrayList<>();
        rowspanCols.add(0);
        rowspanCols.add(1);
        rowspanCols.add(2);
        rowspanCols.add(3);
        int contractRowspanIndex = 3;
        int resourceColNum = 10; // 资源列数
        for (int i = 1; i <= systems.size(); i++) {
            contractRowspanIndex += resourceColNum;
            rowspanCols.add(contractRowspanIndex);
        }
        int spanRownum = 0; // 跨行行标
        Map<String, List<Integer>> rowspan = new HashMap<>(); // 主项目信息跨行数
        for (int i = 0; i < spanrowArr.size(); i++) {
            for (int j = 0; j < rowspanCols.size(); j++) {
                String spankey = String.valueOf(spanRownum + rowspanCols.get(j));
                Integer[] spanrow = {spanrowArr.get(i), 1};
                rowspan.put(spankey, Arrays.asList(spanrow.clone()));
            }
            for (int k = spanRownum + 1; k < spanRownum + spanrowArr.get(i); k++) {
                for (int l = 0; l < rowspanCols.size(); l++) {
                    String spankey = String.valueOf(k + rowspanCols.get(l));
                    Integer[] spanrow = {0, 0};
                    rowspan.put(spankey, Arrays.asList(spanrow.clone()));
                }
            }
            spanRownum += spanrowArr.get(i);
        }
        log.info("rowspan:{}", new ObjectMapper().writeValueAsString(rowspan));

        // ------------输出excel------------
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("项目资源");
        HSSFRow row = sheet.createRow((short) 0);// 第一行
        // 定制表头
        List<String> header = new ArrayList<>();
        header.add("项目名称");
        header.add("问题");
        header.add("板块");
        header.add("上线时间");
        systems.forEach(system -> {
            String systemName = context.getDictCache().get("SYSTEM").get(system);
            header.add("加入时间");
            header.add("前序项目");
            header.add("姓名");
            header.add("供应商");
            header.add("类别");
            header.add("级别");
            header.add("占比");
            header.add("后续项目");
            header.add("释放时间");
            header.add("合同");
        });
    }
}
