package com.nbcb.toolbox.project;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Config
 *
 * @Author jiangyonghua
 * @Date 2022/9/5 17:41
 * @Version 1.0
 **/
@Configuration
@EnableCaching
public class Config {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 设置访问源地址
        corsConfiguration.addAllowedOrigin("*");
        // 设置访问源请求头
        corsConfiguration.addAllowedHeader("*");
        // 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");
        // 设置是否支持用户凭证
        // corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        //项目中有多个filter时此处设置改CorsFilter的优先执行顺序
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
