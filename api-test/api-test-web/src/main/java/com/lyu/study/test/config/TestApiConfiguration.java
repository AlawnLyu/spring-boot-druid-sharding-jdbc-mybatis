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
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
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

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(getShardingDataSource());
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private DataSource getShardingDataSource() throws SQLException, IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(),
                ctx.getResource(shardingConfigFile).getFile());
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2, 1);
        result.put("ds_0", dataSourceOne());
        result.put("ds_1", dataSourceTwo());
        return result;
    }
}
