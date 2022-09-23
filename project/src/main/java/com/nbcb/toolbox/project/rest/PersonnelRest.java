package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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
    private PersonnelRepository personnelRepository;

    @GetMapping("/load")
    public ResponseEntity<List<Personnel>> load() {
        return new ResponseEntity<>(personnelRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Personnel>> query(@PathVariable("page") int page,
                                                         @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Personnel> example = Example.of(Personnel.builder().name(URLDecoder.decode(params, Constant.ENCODING))
                .build(), matcher);
        return new ResponseEntity<>(personnelRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Personnel personnel) {
        personnelRepository.save(personnel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        try {
            personnelRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Constant.DATA_INTEGRITY_ERROR_TIP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
