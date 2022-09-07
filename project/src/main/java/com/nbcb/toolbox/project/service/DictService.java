package com.nbcb.toolbox.project.service;

import com.nbcb.toolbox.project.domain.Dict;
import com.nbcb.toolbox.project.repository.DicTypeRepository;
import com.nbcb.toolbox.project.repository.DictRepository;
import org.junit.jupiter.api.parallel.Isolated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DictService
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 14:48
 * @Version 1.0
 **/
@Service
public class DictService {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DicTypeRepository dicTypeRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void removeType(String code) {
        List<Dict> dicts = dictRepository.findAll(Example.of(Dict.builder().type(code).build()));
        dictRepository.deleteAllInBatch(dicts);
        dicTypeRepository.deleteById(code);
    }
}
