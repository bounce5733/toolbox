package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Contract;
import com.nbcb.toolbox.project.repository.ContractRepository;
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
    private ContractRepository contractRepository;

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
        try {
            contractRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Constant.DATA_INTEGRITY_ERROR_TIP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
