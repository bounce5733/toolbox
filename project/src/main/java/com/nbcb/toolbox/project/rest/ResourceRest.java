package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.repository.ResourceHisRepository;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import com.nbcb.toolbox.project.service.ResourceService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceHisRepository resourceHisRepository;

    @GetMapping("/load")
    public ResponseEntity<List<Resource>> load() {
        return new ResponseEntity<>(resourceRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<List<Map<String, Object>>> query(@RequestParam(name = "dept", required = false) String dept,
                                                           @RequestParam(name = "month", required = false) String month) {
        return new ResponseEntity<>(resourceRepository.findByCustomParams(dept, month), HttpStatus.OK);
    }

    @PostMapping("/query/{page}")
    public ResponseEntity<Page<Map<String, Object>>> query(@PathVariable("page") int page,
                                                           @RequestBody Map<String, String> params) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        String dept = StringUtils.isBlank(params.get("dept")) ? null : params.get("dept");
        Integer team = StringUtils.isBlank(params.get("team")) ? null : Integer.valueOf(params.get("team"));
        String personnelName = StringUtils.isBlank(params.get("personnelName")) ? null : params.get("personnelName");
        String endMonth = StringUtils.isBlank(params.get("endMonth")) ? null : params.get("endMonth");
        String domain = StringUtils.isBlank(params.get("domain")) ? null : params.get("domain");
        Integer hasNextProject = StringUtils.isBlank(params.get("hasNextProject")) ? null :
                Integer.valueOf(params.get("hasNextProject"));
        Page<Map<String, Object>> pageData = resourceRepository.pageFindByCustomParams(dept, team, personnelName,
                endMonth, domain, hasNextProject, pageParam);
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

    @DeleteMapping("/release/{id}")
    public ResponseEntity<Object> release(@PathVariable("id") int id) {
        resourceService.release(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/his/query")
    public ResponseEntity<List<Map<String, Object>>> findResourceHisByCustomParams(
            @RequestParam(name = "dept", required = false) String dept,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "queryFlag") String queryFlag,
            @RequestBody(required = false) List<Object> selData) {
        List<Map<String, Object>> hisResources;
        List<Map<String, Object>> curResources;
        if (queryFlag.equals("line")) { // 查条线
            List<String> domains = new ArrayList<>();
            if (null != selData) {
                selData.forEach(data -> {
                    domains.add(String.valueOf(data));
                });
            }
            hisResources = resourceHisRepository
                    .findByCustomParams(dept, startDate, endDate, domains, new ArrayList<>());
            curResources = resourceRepository
                    .findByCustomParams(dept, startDate, endDate, domains, new ArrayList<>());
        } else { // 查小组
            List<Integer> teams = new ArrayList<>();
            if (null != selData) {
                selData.forEach(data -> {
                    teams.add(Integer.valueOf(String.valueOf(data)));
                });
            }
            hisResources = resourceHisRepository
                    .findByCustomParams(dept, startDate, endDate, new ArrayList<>(), teams);
            curResources = resourceRepository
                    .findByCustomParams(dept, startDate, endDate, new ArrayList<>(), teams);
        }
        hisResources.addAll(curResources);
        return new ResponseEntity<>(hisResources, HttpStatus.OK);
    }
}
