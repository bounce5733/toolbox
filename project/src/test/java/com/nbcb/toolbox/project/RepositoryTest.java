package com.nbcb.toolbox.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.toolbox.project.domain.*;
import com.nbcb.toolbox.project.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 16:11
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DicTypeRepository dicTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceHisRepository resourceHisRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Test
    public void saveDictType() {
        DicType type = new DicType();
        type.setCode("TEST");
        type.setName("测试");
        dicTypeRepository.save(type);
    }

    @Test
    public void removeDict() {
        List<Dict> dicts = dictRepository.findAll(Example.of(Dict.builder().type("TEST").build()));
        dictRepository.deleteAllInBatch(dicts);
    }

    @Test
    public void findAll() throws JsonProcessingException {
        List<Project> projects = projectRepository.findAll();
        log.info("projects:{}", new ObjectMapper().writeValueAsString(projects));
    }

    @Test
    public void saveSubProject() throws JsonProcessingException {
        subProjectRepository.save(SubProject.builder().system("REMOTE_HAWK").projectId(1).build());
        List<SubProject> subProjects = subProjectRepository.findAll();
        log.info("subProjects:{}", new ObjectMapper().writeValueAsString(subProjects));
    }

    @Test
    public void getReousrceBySubProjectId() throws JsonProcessingException {
        List<Resource> resources = resourceRepository.findAll(Example.of(Resource.builder().subProjectId(3).build()));
        log.info("resources:{}", new ObjectMapper().writeValueAsString(resources));
    }

    @Test
    public void pageFindByCustomParams() throws JsonProcessingException {
        Pageable pageParam = PageRequest.of(0, Constant.PAGE_SIZE);
        Page<Map<String, Object>> resources = resourceRepository.pageFindByCustomParams("1", 2,
                null, "2022/09", null, 2, pageParam);
        log.info("resources:{}", new ObjectMapper().writeValueAsString(resources));

    }

    @Test
    public void findResourceByCustomParams() throws JsonProcessingException {
        List<Map<String, Object>> datalist = resourceRepository.findByCustomParams(null, "2022/10/01",
                "2022/10/31", new ArrayList<>(), new ArrayList<>());
        log.info("resources: {}", new ObjectMapper().writeValueAsString(datalist));
    }

    @Test
    public void pageFindSubprojectByCustomParams() throws JsonProcessingException {
        Pageable pageParam = PageRequest.of(0, Constant.PAGE_SIZE);
        subProjectRepository.pageFindByCustomParams(null, null, null, null, null,
                "0",pageParam);
    }

    @Test
    public void pageFindPersonnelIdleByCustomParams() throws JsonProcessingException {
        Pageable pageParam = PageRequest.of(0, Constant.PAGE_SIZE);
        Page<Map<String, Object>> result = personnelRepository
                .pageFindIdleByCustomParams(null, null, null, null, pageParam);
        log.info("result: {}", new ObjectMapper().writeValueAsString(result));
    }
}