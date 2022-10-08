package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Project;
import com.nbcb.toolbox.project.repository.ProjectRepository;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import com.nbcb.toolbox.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * ProjectRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/rest/project")
public class ProjectRest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ResourceRepository subProjectPersonnelRepository;

    @Autowired
    private ProjectService projectService;

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
        try {
            projectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Constant.DATA_INTEGRITY_ERROR_TIP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(Constant.ENCODING);
        String fileName = URLEncoder.encode(ProjectService.PROJECT_EXCEL_FILE_NAME, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName
                + ProjectService.PROJECT_EXCEL_FILE_EXT);
        projectService.exportExcel(response.getOutputStream());
    }
}
