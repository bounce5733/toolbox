package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ProjectRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/subproject")
public class SubProjectRest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ResourceRepository subProjectPersonnelRepository;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<SubProject>> query(@PathVariable("page") int page) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        return new ResponseEntity<>(subProjectRepository.findAll(pageParam), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody SubProject subProject) {
        subProjectRepository.save(subProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        subProjectRepository.deleteById(id);
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
