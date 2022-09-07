package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.DicType;
import com.nbcb.toolbox.project.domain.Dict;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import com.nbcb.toolbox.project.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RestController
@RequestMapping("/rest/personnel")
public class PersonnelRest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonnelService personnelService;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Personnel>> getDictByType(@PathVariable("page") int page,
                                                         @RequestParam("params") String params)
            throws UnsupportedEncodingException {
        return new ResponseEntity<>(personnelService.query(URLDecoder.decode(params, Constant.ENCODING), page),
        HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Personnel personnel) {
        personnelRepository.save(personnel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        personnelRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
