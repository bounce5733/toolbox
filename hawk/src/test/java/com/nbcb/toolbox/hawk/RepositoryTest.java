package com.nbcb.toolbox.hawk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.toolbox.hawk.domain.Article;
import com.nbcb.toolbox.hawk.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2022/10/17 16:37
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void finalAll() {
        Iterable<Article> articleList = articleRepository.findAll();
        articleList.forEach(article -> {
            try {
                log.info("article:{}", new ObjectMapper().writeValueAsString(article));
            } catch (JsonProcessingException e) {
                log.info(e.getMessage(), e);
            }
        });
    }

    @Test
    public void save() {
        articleRepository.save(Article.builder().id(1).title("测试标题").build());
    }
}
