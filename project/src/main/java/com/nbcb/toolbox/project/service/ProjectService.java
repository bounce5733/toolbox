package com.nbcb.toolbox.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
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

    // 项目资源列数
    private static final int RESOURCE_COL_NUM = 10;

    public static final String PROJECT_EXCEL_FILE_EXT = ".xlsx";

    public static final String PROJECT_EXCEL_FILE_NAME = "项目资源情况";

    private static final String SHEET_NAME = "项目资源";

    /**
     * 导出excel数据
     *
     * @param out 输出流
     * @throws IOException
     */
    public void exportExcel(OutputStream out) throws IOException {
        Map<Integer, Personnel> personnelMap = new HashMap<>();
        List<Personnel> personnelList = personnelRepository.findAll();
        personnelList.forEach(personnel -> {
            personnelMap.put(personnel.getId(), personnel);
        });
        Map<Integer, Project> projectMap = new HashMap<>(); // 项目id->主项目映射
        List<Project> projectList = projectRepository.findAll();
        projectList.forEach(project -> projectMap.put(project.getId(), project));
        Set<String> systems = new HashSet<>();
        List<Map<String, Object>> projectRows = new ArrayList<>();
        if (null == context.getDictCache().get("DOMAIN") ||
                null == context.getDictCache().get("SYSTEM") ||
                null == context.getDictCache().get("SUPPLIER") ||
                null == context.getDictCache().get("PERSONNEL_TYPE") ||
                null == context.getDictCache().get("PERSONNEL_LEVEL") ||
                null == context.getDictCache().get("CONTRACT_STATUS")) {
            return;
        }
        projectList.forEach(project -> {
            Map<String, Object> projectRow = new HashMap<>();
            Map<String, String> baseinfo = new HashMap<>();
            baseinfo.put("name", project.getName());
            baseinfo.put("issue", project.getIssue());
            baseinfo.put("domain", context.getDictCache().get("DOMAIN").get(project.getDomain()));
            baseinfo.put("onlineDate", project.getOnlineDate());
            projectRow.put("baseinfo", baseinfo);
            Map<Integer, Map<String, String>> subProjectRow = new HashMap<>();
            project.getSubProjects().forEach(subProject -> {
                if (null != this.context.getDictCache().get("SYSTEM").get(subProject.getSystem())
                        && subProject.getIsClose().equals("0")) {
                    systems.add(subProject.getSystem());
                    Integer resourceIndex = 0;
                    for (Resource resource : subProject.getResources()) {
                        subProjectRow.putIfAbsent(resourceIndex, new HashMap<>());
                        Personnel personnel = personnelMap.get(resource.getPersonnelId());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "startDate", resource.getStartDate());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "prevProject", projectMap.get(resource.getPrevProjectId()) == null ? "/" :
                                projectMap.get(resource.getPrevProjectId()).getName());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "personnelName", personnel.getName());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "supplierName", context.getDictCache().get("SUPPLIER").get(personnel.getCompany())
                                == null ? "/" : context.getDictCache().get("SUPPLIER").get(personnel.getCompany()));
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "personnelType", context.getDictCache().get("PERSONNEL_TYPE").get(personnel.getType())
                                == null ? "/" : context.getDictCache().get("PERSONNEL_TYPE").get(personnel.getType()));
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "personnelLevel", context.getDictCache().get("PERSONNEL_LEVEL").get(personnel.getLevel())
                                == null ? "/" : context.getDictCache().get("PERSONNEL_LEVEL").get(personnel.getLevel()));
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "currentRation", String.valueOf(resource.getCurrentRation()));
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "nextProject", projectMap.get(resource.getNextProjectId()) == null ? "/" :
                                projectMap.get(resource.getNextProjectId()).getName());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "endDate", resource.getEndDate());
                        subProjectRow.get(resourceIndex).put(subProject.getSystem() + "_" + "contract", context.getDictCache().get("CONTRACT_STATUS")
                                .get(subProject.getContract().getStatus()) == null ? "/" : context.getDictCache().get("CONTRACT_STATUS")
                                .get(subProject.getContract().getStatus()));
                        resourceIndex++;
                    }
                }
            });
            projectRow.put("subprojects", subProjectRow);
            projectRows.add(projectRow);
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
                        subrow.put(system + "_" + "prevProject", "/");
                        subrow.put(system + "_" + "personnelName", "/");
                        subrow.put(system + "_" + "supplierName", "/");
                        subrow.put(system + "_" + "personnelType", "/");
                        subrow.put(system + "_" + "personnelLevel", "/");
                        subrow.put(system + "_" + "currentRation", "/");
                        subrow.put(system + "_" + "nextProject", "/");
                        subrow.put(system + "_" + "endDate", "/");
                        subrow.put(system + "_" + "contract", "/");
                    }
                });
                row.putAll(subrow);
                projects.add(row);
            });
            if (subProjectRow.size() > 0) {
                spanrowArr.add(subProjectRow.size());
            }
        });
        // 计算跨行数据列
        List<Integer> rowspanCols = new ArrayList<>();
        rowspanCols.add(0);
        rowspanCols.add(1);
        rowspanCols.add(2);
        rowspanCols.add(3);
        int contractRowspanIndex = 3;
        for (int i = 1; i <= systems.size(); i++) {
            contractRowspanIndex += RESOURCE_COL_NUM;
            rowspanCols.add(contractRowspanIndex);
        }
        int spanRownum = 2; // 跨行行标
        List<List<Integer>> rowspan = new ArrayList<>(); // 主项目信息跨行数
        for (int i = 0; i < spanrowArr.size(); i++) {
            for (int j = 0; j < rowspanCols.size(); j++) {
                Integer[] meregedIndexs = {spanRownum, spanRownum + spanrowArr.get(i) - 1, rowspanCols.get(j),
                        rowspanCols.get(j)};
                rowspan.add(Arrays.asList(meregedIndexs));
            }
            spanRownum += spanrowArr.get(i);
        }
        log.info("projects:{}", new ObjectMapper().writeValueAsString(projects));
        this.excel(systems, projects, rowspan, out);
    }

    /**
     * excel文件输出
     *
     * @param systems  全部数据包含的系统列表
     * @param projects 项目按资源分布的明细信息
     * @param rowspan  资源跨行信息
     * @param out      输出流
     * @throws IOException
     */
    private void excel(Set<String> systems, List<Map<String, String>> projects,
                       List<List<Integer>> rowspan, OutputStream out) throws IOException {
        // ------------输出excel------------
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(SHEET_NAME);
        XSSFRow firstRow = sheet.createRow((short) 0);// 第一行

        XSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        headStyle.setWrapText(true);// 自动换行
        XSSFFont font = wb.createFont();
        font.setBold(true);
        headStyle.setFont(font);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);

        // 定制表头
        List<String> firstHeader = new LinkedList<>();
        List<List<Integer>> headerRowspan = new ArrayList<>(); // 表头跨行信息
        Integer[] headerRowspan1 = {0, 1, 0, 0};
        Integer[] headerRowspan2 = {0, 1, 1, 1};
        Integer[] headerRowspan3 = {0, 1, 2, 2};
        Integer[] headerRowspan4 = {0, 1, 3, 3};
        headerRowspan.add(Arrays.asList(headerRowspan1));
        headerRowspan.add(Arrays.asList(headerRowspan2));
        headerRowspan.add(Arrays.asList(headerRowspan3));
        headerRowspan.add(Arrays.asList(headerRowspan4));
        firstHeader.add("项目名称");
        firstHeader.add("问题");
        firstHeader.add("板块");
        firstHeader.add("上线时间");
        int systemCount = 1;
        for (String system : systems) {
            String systemName = context.getDictCache().get("SYSTEM").get(system);
            for (int i = 0; i < RESOURCE_COL_NUM; i++) {
                firstHeader.add(systemName);
            }
            Integer[] headerSystemRowspan = {0, 0, 4 + RESOURCE_COL_NUM * (systemCount - 1),
                    3 + RESOURCE_COL_NUM * systemCount};
            headerRowspan.add(Arrays.asList(headerSystemRowspan));
            systemCount++;
        }
        for (int i = 0; i < firstHeader.size(); i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(firstHeader.get(i));
            cell.setCellStyle(headStyle);
        }

        XSSFRow secondRow = sheet.createRow((short) 1);
        // 定制表头
        List<String> header = new LinkedList<>();
        List<String> headerKeys = new LinkedList<>();
        header.add("项目名称");
        header.add("问题");
        header.add("板块");
        header.add("上线时间");
        headerKeys.add("name");
        headerKeys.add("issue");
        headerKeys.add("domain");
        headerKeys.add("onlineDate");
        systems.forEach(system -> {
            String systemCode = system + "_";
            header.add("加入时间");
            headerKeys.add(systemCode + "startDate");
            header.add("前序项目");
            headerKeys.add(systemCode + "prevProject");
            header.add("姓名");
            headerKeys.add(systemCode + "personnelName");
            header.add("供应商");
            headerKeys.add(systemCode + "supplierName");
            header.add("类别");
            headerKeys.add(systemCode + "personnelType");
            header.add("级别");
            headerKeys.add(systemCode + "personnelLevel");
            header.add("占比");
            headerKeys.add(systemCode + "currentRation");
            header.add("后续项目");
            headerKeys.add(systemCode + "nextProject");
            header.add("释放时间");
            headerKeys.add(systemCode + "endDate");
            header.add("合同");
            headerKeys.add(systemCode + "contract");
        });

        for (int i = 0; i < header.size(); i++) {
            XSSFCell cell = secondRow.createCell(i);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(headStyle);
        }

        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        style.setWrapText(true);// 自动换行

        for (int i = 0; i < projects.size(); i++) {
            XSSFRow row = sheet.createRow(i + 2);
            Map<String, String> rowData = projects.get(i);
            log.info("rowData:{}", new ObjectMapper().writeValueAsString(rowData));
            int cellIndex = 0;
            for (String key : headerKeys) {
                XSSFCell cell = row.createCell(cellIndex);
                cell.setCellValue(rowData.get(key));
                cell.setCellStyle(style);
                cellIndex++;
            }
        }

        // 表头行列合并
        headerRowspan.forEach(indexs -> {
            sheet.addMergedRegion(new CellRangeAddress(indexs.get(0), indexs.get(1), indexs.get(2), indexs.get(3)));
        });
        // 数据行列合并
        rowspan.forEach(indexs -> {
            sheet.addMergedRegion(new CellRangeAddress(indexs.get(0), indexs.get(1), indexs.get(2), indexs.get(3)));
        });

        try {
            wb.write(out);
            out.flush();
        } finally {
            out.close();
        }
    }
}
