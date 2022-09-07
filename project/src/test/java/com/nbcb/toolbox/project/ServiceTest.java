package com.nbcb.toolbox.project;

import com.google.gson.Gson;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.service.PersonnelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ServiceTest
 *
 * @Author jiangyonghua
 * @Date 2022/9/7 09:51
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private PersonnelService personnelService;

    @Test
    public void personnelQuery() {
        Page<Personnel> datalist = personnelService.query("ddc", 0);
        log.info("datalist:{}", new Gson().toJson(datalist));
    }
}
