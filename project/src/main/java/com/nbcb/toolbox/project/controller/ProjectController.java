package com.nbcb.toolbox.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ProjectController
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 14:53
 * @Version 1.0
 **/
@Controller
@RequestMapping("/project")
public class ProjectController {

    @RequestMapping("/demo")
    public String demo(Model model) {
        return "demo";
    }
}
