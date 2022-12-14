package com.nbcb.toolbox.project;

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

    @RequestMapping("/resource/team")
    public String resourceTeam(Model model) {
        return "resource/team";
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

    @RequestMapping("/analysis/devLeverage")
    public String devLeverage(Model model) {
        return "analysis/devLeverage";
    }

    @RequestMapping("/analysis/resourceAnalysis")
    public String analysisResource(Model model) {
        return "analysis/resourceAnalysis";
    }

    @RequestMapping("/analysis/lineAnalysis")
    public String analysisLine(Model model) {
        return "analysis/lineAnalysis";
    }

    @RequestMapping("/analysis/teamAnalysis")
    public String analysisTeam(Model model) {
        return "analysis/teamAnalysis";
    }

    @RequestMapping("/analysis/idleResources")
    public String analysisIdleResources(Model model) {
        return "analysis/idleResources";
    }
}
