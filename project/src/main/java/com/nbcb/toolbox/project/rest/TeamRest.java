package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.domain.Team;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import com.nbcb.toolbox.project.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Context context;

    @Autowired
    private PersonnelRepository personnelRepository;

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
        StringBuilder dataIntegrityMsg = new StringBuilder("请先删除");
        boolean isVaild = true;
        // 检查人员引用
        List<Personnel> personnels = personnelRepository.findAll(Example.of(Personnel.builder().team(
                Team.builder().id(id).build()).build()));
        if (personnels.size() > 0) {
            dataIntegrityMsg.append("人员【" + personnels.get(0).getName() + "】");
            isVaild = false;
        }
        dataIntegrityMsg.append("的引用！");
        if (isVaild) {
            teamRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(dataIntegrityMsg, HttpStatus.OK);
        }
    }

}
