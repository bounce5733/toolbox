package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Risk;
import com.nbcb.toolbox.project.repository.RiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * RiskRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/risk")
public class RiskRest {

    @Autowired
    private RiskRepository riskRepository;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Risk>> query(@PathVariable("page") int page, @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("desc", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Risk> example = Example.of(Risk.builder().desc(URLDecoder.decode(params, Constant.ENCODING))
                .build(), matcher);
        return new ResponseEntity<>(riskRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Risk risk) {
        riskRepository.save(risk);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        riskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subproject/{id}")
    public ResponseEntity<List<Risk>> getRisks(@PathVariable("id") int id) {
        List<Risk> resources = riskRepository.findAll(Example.of(Risk.builder().subProjectId(id).build()));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
