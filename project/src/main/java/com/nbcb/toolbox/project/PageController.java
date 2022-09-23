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

    @RequestMapping("/project/create")
    public String projectCreate(Model model) {
        return "project/create";
    }

    @RequestMapping("/project/manage")
    public String projectManage(Model model) {
        return "project/manage";
    }

    @RequestMapping("/project/outline")
    public String projectOutline(Model model) {
        return "project/outline";
    }

    @RequestMapping("/analysis/resourceLoad")
    public String analysisResourceLoad(Model model) {
        return "analysis/resourceLoad";
    }

    @RequestMapping("/analysis/resourceRelease")
    public String analysisResourceRelease(Model model) {
        return "analysis/resourceRelease";
    }

    @RequestMapping("/analysis/teamAnalysis")
    public String analysisTeam(Model model) {
        return "analysis/teamAnalysis";
    }
}
