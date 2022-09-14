package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
