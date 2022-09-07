package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Contract;
import com.nbcb.toolbox.project.repository.ContractRepository;
import com.nbcb.toolbox.project.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * ContractRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/contract")
public class ContractRest {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractService contractService;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Contract>> getDictByType(@PathVariable("page") int page,
                                                        @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        return new ResponseEntity<>(contractService.query(URLDecoder.decode(params, Constant.ENCODING), page),
        HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Contract contract) {
        contractRepository.save(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        contractRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
