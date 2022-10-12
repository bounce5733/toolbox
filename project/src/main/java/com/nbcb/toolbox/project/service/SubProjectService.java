package com.nbcb.toolbox.project.service;

import com.nbcb.toolbox.project.domain.Process;
import com.nbcb.toolbox.project.domain.*;
import com.nbcb.toolbox.project.repository.ProcessRepository;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import com.nbcb.toolbox.project.repository.RiskRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * SubProjectService
 *
 * @author: yh.jiang
 * @time: 2022/10/12 14:12
 */
@Slf4j
@Service
public class SubProjectService {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RiskRepository riskRepository;

    public Page<SubProject> query(Map<String, String> params, Pageable pageParam) {
        String name = StringUtils.isBlank(params.get("name")) ? null : params.get("name");
        String dept = StringUtils.isBlank(params.get("dept")) ? null : params.get("dept");
        String domain = StringUtils.isBlank(params.get("domain")) ? null : params.get("domain");
        String system = StringUtils.isBlank(params.get("system")) ? null : params.get("system");
        String isClose = StringUtils.isBlank(params.get("isClose")) ? null : params.get("isClose");
        Page<Map<String, Object>> pagedata = subProjectRepository
                .pageFindByCustomParams(name, dept, domain, system, isClose, pageParam);
        List<SubProject> subProjectList = new ArrayList<>();
        pagedata.getContent().forEach(item -> {
            int id = Integer.valueOf(String.valueOf(item.get("ID")));
            List<Process> processes = processRepository.findAll(Example.of(Process.builder().subProjectId(id).build()));
            List<Resource> resources = resourceRepository.findAll(Example.of(Resource.builder().subProjectId(id).build()));
            List<Risk> risks = riskRepository.findAll(Example.of(Risk.builder().subProjectId(id).build()));
            Contract contract = Contract.builder()
                    .name(String.valueOf(item.get("CONTRACT_NAME")))
                    .type(String.valueOf(item.get("CONTRACT_TYPE")))
                    .startDate(String.valueOf(item.get("START_DATE")))
                    .endDate(String.valueOf(item.get("END_DATE")))
                    .company(String.valueOf(item.get("COMPANY")))
                    .status(String.valueOf(item.get("STATUS"))).build();
            subProjectList.add(SubProject.builder()
                    .id(Integer.valueOf(String.valueOf(item.get("ID"))))
                    .dept(null == item.get("DEPT") ? null : String.valueOf(item.get("DEPT")))
                    .phase(null == item.get("PHASE") ? null : String.valueOf(item.get("PHASE")))
                    .system(null == item.get("SYSTEM") ? null : String.valueOf(item.get("SYSTEM")))
                    .isClose(String.valueOf(item.get("IS_CLOSE")))
                    .pm(null == item.get("PM") ? null : Integer.valueOf(String.valueOf(item.get("PM"))))
                    .pmo(null == item.get("PMO") ? null : Integer.valueOf(String.valueOf(item.get("PMO"))))
                    .qa(null == item.get("QA") ? null : Integer.valueOf(String.valueOf(item.get("QA"))))
                    .contract(contract)
                    .processes(processes)
                    .resources(resources)
                    .risks(risks).build());
        });
        return new Page<SubProject>() {
            @Override
            public int getTotalPages() {
                return pagedata.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return pagedata.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super SubProject, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return pagedata.getNumber();
            }

            @Override
            public int getSize() {
                return pagedata.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return pagedata.getNumberOfElements();
            }

            @Override
            public List<SubProject> getContent() {
                return subProjectList;
            }

            @Override
            public boolean hasContent() {
                return pagedata.hasContent();
            }

            @Override
            public Sort getSort() {
                return pagedata.getSort();
            }

            @Override
            public boolean isFirst() {
                return pagedata.isFirst();
            }

            @Override
            public boolean isLast() {
                return pagedata.isLast();
            }

            @Override
            public boolean hasNext() {
                return pagedata.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return pagedata.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return pagedata.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return pagedata.previousPageable();
            }

            @Override
            public Iterator<SubProject> iterator() {
                return subProjectList.iterator();
            }
        };
    }
}
