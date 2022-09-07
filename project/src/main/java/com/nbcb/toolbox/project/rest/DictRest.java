package com.nbcb.toolbox.project.rest;

import com.nbcb.toolbox.project.Context;
import com.nbcb.toolbox.project.domain.DicType;
import com.nbcb.toolbox.project.domain.Dict;
import com.nbcb.toolbox.project.repository.DicTypeRepository;
import com.nbcb.toolbox.project.repository.DictRepository;
import com.nbcb.toolbox.project.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * DictRest
 *
 * @Author jiangyonghua
 * @Date 2022/9/6 08:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rest/dict")
public class DictRest {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DicTypeRepository dicTypeRepository;

    @Autowired
    private DictService dictService;

    @Autowired
    private Context context;

    @GetMapping("/load")
    public ResponseEntity<Map<String, Map<String, String>>> load() {
        return new ResponseEntity<>(context.getDictCache(), HttpStatus.OK);
    }

    @PostMapping
    @CacheEvict(cacheNames = Context.DICT_CACHE_KEY, allEntries = true)
    public ResponseEntity<String> save(@RequestBody Dict dict) {
        dictRepository.save(dict);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = Context.DICT_CACHE_KEY, allEntries = true)
    public ResponseEntity<Object> remove(@PathVariable("id") int id) {
        dictRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/type/{code}")
    public ResponseEntity<List<Dict>> getDictByType(@PathVariable("code") String code) {
        return new ResponseEntity<>(dictRepository.findAll(Example.of(Dict.builder().type(code).build())), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<DicType>> loadType() {
        return new ResponseEntity<>(dicTypeRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/type/{code}")
    @CacheEvict(cacheNames = Context.DICT_CACHE_KEY, allEntries = true)
    public ResponseEntity<Object> removeType(@PathVariable("code") String code) {
        dictService.removeType(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/type")
    @CacheEvict(cacheNames = Context.DICT_CACHE_KEY, allEntries = true)
    public ResponseEntity<String> saveType(@RequestBody DicType dictype) {
        dicTypeRepository.save(dictype);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
