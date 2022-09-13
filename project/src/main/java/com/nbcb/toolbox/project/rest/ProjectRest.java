package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Contract;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.domain.SubProjectPersonnel;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import com.nbcb.toolbox.project.repository.SubProjectPersonnelRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * ProjectRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/project")
public class ProjectRest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private SubProjectPersonnelRepository subProjectPersonnelRepository;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Project>> query(@PathVariable("page") int page, @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Project> example = Example.of(Project.builder().name(URLDecoder.decode(params, Constant.ENCODING))
                .build(), matcher);
        return new ResponseEntity<>(projectRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @GetMapping("/load")
    public ResponseEntity<List<Project>> load() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Project project) {
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        projectRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/subProject")
    public ResponseEntity<Object> saveSubProject(@RequestBody SubProject subProject) {
        subProjectRepository.save(subProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subProject/{id}")
    public ResponseEntity<Object> removeSubProject(@PathVariable("id") int id) {
        subProjectRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/subProjectPersonnel")
    public ResponseEntity<Object> saveSubProjectPersonnel(@RequestBody SubProjectPersonnel subProjectPersonnel) {
        subProjectPersonnelRepository.save(subProjectPersonnel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subProjectPersonnel/{id}")
    public ResponseEntity<Object> removeSubProjectPersonnel(@PathVariable("id") int id) {
        subProjectPersonnelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
