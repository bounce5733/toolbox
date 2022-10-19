package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.Contract;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.repository.ContractRepository;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * ContractRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/rest/contract")
public class ContractRest {

    @Autowired
    private Context context;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Contract>> query(@PathVariable("page") int page, @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Contract> example = Example.of(Contract.builder().name(URLDecoder.decode(params, Constant.ENCODING))
                .build(), matcher);
        return new ResponseEntity<>(contractRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @GetMapping("/load")
    public ResponseEntity<List<Contract>> load() {
        return new ResponseEntity<>(contractRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Contract contract) {
        contractRepository.save(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        StringBuilder dataIntegrityMsg = new StringBuilder("请先删除");
        boolean isVaild = true;
        // 检查子项引用
        List<SubProject> subProjects = subProjectRepository.findAll(Example.of(SubProject.builder()
                .contract(Contract.builder().id(id).build()).build()));
        if (subProjects.size() > 0) {
            Project project = projectRepository.findById(subProjects.get(0).getProjectId()).get();
            dataIntegrityMsg.append("项目【" + project.getName() + "】系统【" + context.getDictCache().get("SYSTEM")
                    .get(subProjects.get(0).getSystem()) + "】中合同");
            isVaild = false;
        }
        dataIntegrityMsg.append("的引用！");
        if (isVaild) {
            contractRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(dataIntegrityMsg, HttpStatus.OK);
        }
    }

}
