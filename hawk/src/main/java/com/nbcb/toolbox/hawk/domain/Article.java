package com.nbcb.toolbox.hawk.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Article
 *
 * @Author jiangyonghua
 * @Date 2022/10/17 16:01
 * @Version 1.0
 **/
@Data
@Builder
@Document(indexName = "product")
public class Article implements Serializable {

    @Id
    private Integer id;

    private String title;
}

