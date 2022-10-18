package com.nbcb.toolbox.hawk.repository;

import com.nbcb.toolbox.hawk.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2022/10/17 16:00
 * @Version 1.0
 **/
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {

}
