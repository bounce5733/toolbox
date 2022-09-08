package com.nbcb.toolbox.project;

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
@org.springframework.stereotype.Controller
public class PageController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping("/resource/dict")
    public String dict(Model model) {
        return "resource/dict";
    }

    @RequestMapping("/resource/personnel")
    public String resourcePersonnel(Model model) {
        return "resource/personnel";
    }

    @RequestMapping("/resource/contract")
    public String resourceContract(Model model) {
        return "resource/contract";
    }

    @RequestMapping("/resource/risk")
    public String resourceRisk(Model model) {
        return "resource/risk";
    }

    @RequestMapping("/resource/project")
    public String resourceProject(Model model) {
        return "resource/project";
    }

    @RequestMapping("/assemble/project")
    public String assembleProject(Model model) {
        return "assemble/project";
    }

    @RequestMapping("/assemble/adjust")
    public String assembleAdjust(Model model) {
        return "assemble/adjust";
    }

    @RequestMapping("/analysis/invest")
    public String analysisInvest(Model model) {
        return "analysis/invest";
    }

    @RequestMapping("/analysis/release")
    public String analysisRelease(Model model) {
        return "analysis/release";
    }
}
