package com.nbcb.toolbox.project;

import com.nbcb.toolbox.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;

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
    public void exportExcel() throws IOException {
        String filename = "/Users/jiangyonghua/" + ProjectService.PROJECT_EXCEL_FILE_NAME
                + ProjectService.PROJECT_EXCEL_FILE_EXT;
        FileOutputStream fos = new FileOutputStream(filename);
        projectService.exportExcel(fos);
    }
}
