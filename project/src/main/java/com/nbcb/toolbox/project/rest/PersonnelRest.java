package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.*;
import com.nbcb.toolbox.project.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * PersonnelRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/rest/personnel")
public class PersonnelRest {

    @Autowired
    private Context context;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ResourceRepository subProjectPersonnelRepository;

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/load")
    public ResponseEntity<List<Personnel>> load() {
        return new ResponseEntity<>(personnelRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Personnel>> query(@PathVariable("page") int page,
                                                 @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, 100);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Personnel> example = Example.of(Personnel.builder().name(URLDecoder.decode(params, Constant.ENCODING))
                .build(), matcher);
        return new ResponseEntity<>(personnelRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @PostMapping("/query/idle/{page}")
    public ResponseEntity<Page<Map<String, Object>>> queryIdleByCustomParams(@PathVariable("page") int page,
                                                                             @RequestBody Map<String, String> params) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        String name = StringUtils.isBlank(params.get("name")) ? null : params.get("name");
        String position = StringUtils.isBlank(params.get("position")) ? null : params.get("position");
        String company = StringUtils.isBlank(params.get("company")) ? null : params.get("company");
        String dept = StringUtils.isBlank(params.get("dept")) ? null : params.get("dept");
        return new ResponseEntity<>(personnelRepository
                .pageFindIdleByCustomParams(name, position, company, dept, pageParam), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Personnel personnel) {
        personnelRepository.save(personnel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        StringBuilder dataIntegrityMsg = new StringBuilder("请先删除");
        boolean isVaild = true;
        List<Resource> resources;
        SubProject subProject;
        Project project;
        // 检查资源项目人员引用
        resources = resourceRepository.findAll(Example.of(Resource.builder().personnelId(id).build()));
        if (resources.size() > 0) {
            subProject = subProjectRepository.findById(resources.get(0).getSubProjectId()).get();
            project = projectRepository.findById(subProject.getProjectId()).get();
            dataIntegrityMsg.append("项目【" + project.getName() + "】系统【" + context.getDictCache().get("SYSTEM")
                    .get(subProject.getSystem()) + "】中资源项目人员");
            isVaild = false;
        }
        // 检查风险负责人引用
        if (isVaild) {
            List<Risk> risks = riskRepository.findAll(Example.of(Risk.builder().responsiblePersonnelId(id).build()));
            if (risks.size() > 0) {
                subProject = subProjectRepository.findById(risks.get(0).getSubProjectId()).get();
                project = projectRepository.findById(subProject.getProjectId()).get();
                dataIntegrityMsg.append("项目【" + project.getName() + "】系统【" + context.getDictCache().get("SYSTEM")
                        .get(subProject.getSystem()) + "】中风险负责人");
                isVaild = false;
            }
        }
        dataIntegrityMsg.append("的引用！");
        if (isVaild) {
            personnelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(dataIntegrityMsg, HttpStatus.OK);
        }
    }

}
