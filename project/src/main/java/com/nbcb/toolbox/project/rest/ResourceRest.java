package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ResourceRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/resource")
public class ResourceRest {

    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping("/load")
    public ResponseEntity<List<Resource>> load() {
        return new ResponseEntity<>(resourceRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/query/{page}")
    public ResponseEntity<Page<Map<String, Object>>> query(@PathVariable("page") int page, @RequestBody Map<String, String> params) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        String dept = StringUtils.isBlank(params.get("dept")) ? null : params.get("dept");
        String team = StringUtils.isBlank(params.get("team")) ? null : params.get("team");
        String personnelName = StringUtils.isBlank(params.get("personnelName")) ? null : params.get("personnelName");
        String endMonth = StringUtils.isBlank(params.get("endMonth")) ? null : params.get("endMonth");
        String domain = StringUtils.isBlank(params.get("domain")) ? null : params.get("domain");
        Integer hasNextSubproject = StringUtils.isBlank(params.get("hasNextSubproject")) ? null :
                Integer.valueOf(params.get("hasNextSubproject"));
        Page<Map<String, Object>> pageData = resourceRepository.findResourceByCustomParams(dept, team, personnelName,
                endMonth, domain, hasNextSubproject, pageParam);
        return new ResponseEntity<>(pageData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Resource resource) {
        resourceRepository.save(resource);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        resourceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subproject/{id}")
    public ResponseEntity<List<Resource>> getResouces(@PathVariable("id") int id) {
        List<Resource> resources = resourceRepository.findAll(Example.of(Resource.builder().subProjectId(id).build()));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
