package com.nbcb.toolbox.project.service;

import com.nbcb.toolbox.project.Constant;
import com.nbcb.toolbox.project.domain.Personnel;
import com.nbcb.toolbox.project.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * PersonnelService
 *
 * @Author jiangyonghua
 * @Date 2022/9/7 09:37
 * @Version 1.0
 **/
@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    public Page<Personnel> query(String params, int page) {
        Pageable pageParam = PageRequest.of(page - 1, Constant.PAGE_SIZE);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Personnel> example = Example.of(Personnel.builder().name(params).build(), matcher);
        return personnelRepository.findAll(example, pageParam);
    }
}
