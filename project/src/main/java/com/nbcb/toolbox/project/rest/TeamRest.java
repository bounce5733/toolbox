package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Team;
import com.nbcb.toolbox.project.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
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
 * TeamRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/rest/team")
public class TeamRest {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/query/{page}")
    public ResponseEntity<Page<Team>> query(@PathVariable("page") int page, @RequestParam("name") String name,
                                            @RequestParam("dept") String dept)
            throws UnsupportedEncodingException {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        Team params = Team.builder().name(URLDecoder.decode(name, Constant.ENCODING)).build();
        if (StringUtils.isNotBlank(dept)) {
            params.setDept(URLDecoder.decode(dept, Constant.ENCODING));
        }
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Team> example = Example.of(params, matcher);
        return new ResponseEntity<>(teamRepository.findAll(example, pageParam), HttpStatus.OK);
    }

    @GetMapping("/load")
    public ResponseEntity<List<Team>> load() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Team team) {
        teamRepository.save(team);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        try {
            teamRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getRootCause().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
