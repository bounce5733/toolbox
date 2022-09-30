package com.nbcb.toolbox.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nbcb.toolbox.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ServiceTest
 *
 * @Author jiangyonghua
 * @Date 2022/9/29 16:09
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void exportExcel() throws JsonProcessingException {
        projectService.exportExcel();
    }
}
