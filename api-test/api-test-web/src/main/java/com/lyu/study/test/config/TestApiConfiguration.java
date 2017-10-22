/**
 * @author LYU
 * @create 2017年10月09日 14:34
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestApiConfiguration {

    @Value("${sharding.config_file}")
    private String shardingConfigFile = "";

    /**
     * @author: LYU
     * @description: 根据application.yml文件中的配置,创建druiddatasource,指定one配置作为主配置
     * @method: dataSourceOne
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月16日 10:04:07
     */
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 根据application.yml文件中的配置,创建druiddatasource
     * @method: dataSourceTwo
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月16日 10:05:13
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: sqlSessionFactoryBean,指定使用shardingdatasource,这样配置过后,mybatis才会使用sharding源
     * @method: sqlSessionFactoryBean
     * @param
     * @return: org.apache.ibatis.session.SqlSessionFactory
     * @date: 2017年10月16日 10:02:44
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(getShardingDataSource());
        return bean.getObject();
    }

    /**
     * @author: LYU
     * @description: 创建SqlSessionTemplateBean
     * @method: sqlSessionTemplate
     * @param sqlSessionFactory
     * @return: org.mybatis.spring.SqlSessionTemplate
     * @date: 2017年10月16日 10:05:40
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * @author: LYU
     * @description: 获取shardingdatasource
     * @method: getShardingDataSource
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月15日 15:18:59
     */
    private DataSource getShardingDataSource() throws SQLException, IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(),
                ctx.getResource(shardingConfigFile).getFile());
    }

    /**
     * @author: LYU
     * @description: 根据druid配置的源获取datasourcemap
     * @method: createDataSourceMap
     * @param
     * @return: java.util.Map<java.lang.String,javax.sql.DataSource>
     * @date: 2017年10月16日 10:00:52
     */
    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2, 1);
        result.put("ds_0", dataSourceOne());
        result.put("ds_1", dataSourceTwo());
        return result;
    }
}
