package com.nbcb.toolbox.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nbcb.toolbox.project.domain.DicType;
import com.nbcb.toolbox.project.domain.Dict;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.repository.DictRepository;
import com.nbcb.toolbox.project.repository.DicTypeRepository;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
