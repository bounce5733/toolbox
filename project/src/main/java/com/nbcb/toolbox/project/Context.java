package com.nbcb.toolbox.project;

import com.nbcb.toolbox.project.domain.Dict;
import com.nbcb.toolbox.project.repository.DictRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Context
 *
 * @Author jiangyonghua
 * @Date 2022/9/7 11:06
 * @Version 1.0
 **/
@Slf4j
@Component
public class Context {

    public static final String DICT_CACHE_KEY = "SYS_DICT_CACHE";

    @Autowired
    private DictRepository dictRepository;

    @PostConstruct
    @Cacheable(cacheNames = DICT_CACHE_KEY)
    public Map<String, Map<String, String>> getDictCache() {
        log.info("加载系统字典...");
        Map<String, Map<String, String>> dictMap = new HashMap<>();
        List<Dict> dictList = dictRepository.findAll();
        dictList.forEach(dict -> {
           if (dictMap.get(dict.getType()) == null) {
               Map<String, String> typeMap = new HashMap<>();
               dictMap.put(dict.getType(), typeMap);
           }
           dictMap.get(dict.getType()).put(dict.getCode(), dict.getName());
        });
        return dictMap;
    }
}
