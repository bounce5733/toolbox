package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.domain.Process;
import com.nbcb.toolbox.project.repository.ProcessRepository;
import com.nbcb.toolbox.project.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProcessRest
 *
 * @author: yh.jiang
 * @time: 2022/9/22 16:15
 */
@RestController
@RequestMapping("/rest/process")
public class ProcessRest {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessService processService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Process process) {
        processService.save(process);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        processRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subproject/{id}")
    public ResponseEntity<List<Process>> getProcesses(@PathVariable("id") int id) {
        List<Process> processes = processRepository.findAll(Example.of(Process.builder().subProjectId(id).build()));
        return new ResponseEntity<>(processes, HttpStatus.OK);
    }
}
