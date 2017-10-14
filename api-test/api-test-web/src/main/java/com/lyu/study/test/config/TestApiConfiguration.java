/**
 * @author LYU
 * @create 2017年10月09日 14:34
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.config;

import com.lyu.study.test.TestApiStarter;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingjdbc.core.yaml.sharding.YamlShardingDataSource;
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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestApiConfiguration {

    @Value("${sharding.config_file}")
    private String shardingConfigFile = "";

    @Bean(name="db_0")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.db_0")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db_1")
    @ConfigurationProperties(prefix = "spring.datasource.db_1")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
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
        result.put("ds_0", primaryDataSource());
        result.put("ds_1", secondDataSource());
        return result;
    }
}
