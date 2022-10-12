package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import com.nbcb.toolbox.project.service.SubProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ProjectRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/rest/subproject")
public class SubProjectRest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ResourceRepository subProjectPersonnelRepository;

    @Autowired
    private SubProjectService subProjectService;

    @GetMapping("/load")
    public ResponseEntity<List<SubProject>> load() {
        return new ResponseEntity<>(subProjectRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/query/{page}")
    public ResponseEntity<Page<SubProject>> query(@PathVariable("page") int page,
                                                  @RequestBody Map<String, String> params) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        return new ResponseEntity<>(subProjectService.query(params, pageParam), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody SubProject subProject) {
        subProjectRepository.save(subProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        try {
            subProjectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Constant.DATA_INTEGRITY_ERROR_TIP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/close/{id}/{status}")
    public ResponseEntity<Object> close(@PathVariable("id") int id, @PathVariable("status") String status) {
        SubProject subProject = subProjectRepository.findById(id).get();
        subProject.setIsClose(status);
        subProjectRepository.save(subProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/subProjectPersonnel")
    public ResponseEntity<Object> saveSubProjectPersonnel(@RequestBody Resource subProjectPersonnel) {
        subProjectPersonnelRepository.save(subProjectPersonnel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subProjectPersonnel/{id}")
    public ResponseEntity<Object> removeSubProjectPersonnel(@PathVariable("id") int id) {
        subProjectPersonnelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
