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

    @Value("${sharding.sharding_config_file}")
    private String sharding_config_file = "";

    @Value("${sharding.sharding_ms_config_file}")
    private String sharding_ms_config_file = "";

    @Value("${sharding.env}")
    private String env = "";

    /**
     * @param
     * @author: LYU
     * @description: 根据application.yml文件中的配置, 创建druiddatasource, 指定one配置作为主配置
     * @method: dataSourceOne
     * @return: javax.sql.DataSource
     * @date: 2017年10月16日 10:04:07
     */
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @param
     * @author: LYU
     * @description: 根据application.yml文件中的配置, 创建druiddatasource
     * @method: dataSourceTwo
     * @return: javax.sql.DataSource
     * @date: 2017年10月16日 10:05:13
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建主库master0 的数据源
     * @method: dataSourceMaster0
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:32:10
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master0")
    public DataSource dataSourceMaster0() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建master0 的从库 slave0 数据源
     * @method: dataSourceMaster0Slave0
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:33:01
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master0slave0")
    public DataSource dataSourceMaster0Slave0() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建master0 的从库 slave1 数据源
     * @method: dataSourceMaster0Slave1
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:33:42
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master0slave1")
    public DataSource dataSourceMaster0Slave1() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建主库master1 的数据源
     * @method: dataSourceMaster1
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:34:07
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master1")
    public DataSource dataSourceMaster1() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建master1 的从库 slave0 数据源
     * @method: dataSourceMaster1Slave0
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:34:45
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master1slave0")
    public DataSource dataSourceMaster1Slave0() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @author: LYU
     * @description: 创建master1 的从库 slave1 数据源
     * @method: dataSourceMaster1Slave1
     * @param
     * @return: javax.sql.DataSource
     * @date: 2017年10月24日 16:35:15
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master1slave1")
    public DataSource dataSourceMaster1Slave1() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @param
     * @author: LYU
     * @description: sqlSessionFactoryBean, 指定使用shardingdatasource, 这样配置过后, mybatis才会使用sharding源
     * @method: sqlSessionFactoryBean
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
     * @param sqlSessionFactory
     * @author: LYU
     * @description: 创建SqlSessionTemplateBean
     * @method: sqlSessionTemplate
     * @return: org.mybatis.spring.SqlSessionTemplate
     * @date: 2017年10月16日 10:05:40
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * @param
     * @author: LYU
     * @description: 获取shardingdatasource
     * @method: getShardingDataSource
     * @return: javax.sql.DataSource
     * @date: 2017年10月15日 15:18:59
     */
    private DataSource getShardingDataSource() throws SQLException, IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        Map<String, DataSource> dataSourceMap;
        String config_file;
        if ("shardingOnly".equals(env)) {
            dataSourceMap = createDataSourceMap();
            config_file = sharding_config_file;
        } else {
            dataSourceMap = createMasterSlaveDataSourceMap();
            config_file = sharding_ms_config_file;
        }
        return ShardingDataSourceFactory.createDataSource(dataSourceMap,
                ctx.getResource(config_file).getFile());
    }

    /**
     * @param
     * @author: LYU
     * @description: 根据druid配置的源获取datasourcemap
     * @method: createDataSourceMap
     * @return: java.util.Map<java.lang.String,javax.sql.DataSource>
     * @date: 2017年10月16日 10:00:52
     */
    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2, 1);
        result.put("ds_0", dataSourceOne());
        result.put("ds_1", dataSourceTwo());
        return result;
    }

    /**
     * @author: LYU
     * @description: 创建读写分离+分库分表数据源
     * @method: createMasterSlaveDataSourceMap
     * @param
     * @return: java.util.Map<java.lang.String,javax.sql.DataSource>
     * @date: 2017年10月24日 16:30:39
     */
    private Map<String, DataSource> createMasterSlaveDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2, 1);
        result.put("ds_master_0", dataSourceMaster0());
        result.put("ds_master_0_slave_0", dataSourceMaster0Slave0());
        result.put("ds_master_0_slave_1", dataSourceMaster0Slave1());
        result.put("ds_master_1", dataSourceMaster1());
        result.put("ds_master_1_slave_0", dataSourceMaster1Slave0());
        result.put("ds_master_1_slave_1", dataSourceMaster1Slave1());
        return result;
    }
}
