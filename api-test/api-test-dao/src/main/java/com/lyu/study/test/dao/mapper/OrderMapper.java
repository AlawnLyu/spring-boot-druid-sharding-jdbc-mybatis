/**
 * @author LYU
 * @create 2017-10-13-11:15
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.mapper;

import com.lyu.study.test.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {
    @Update("CREATE TABLE IF NOT EXISTS t_order (order_id BIGINT AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id));")
    void createIfNotExistsTable();

    @Update("TRUNCATE TABLE t_order;")
    void truncateTable();

    @Insert("INSERT INTO t_order (\n" +
            "          user_id, status\n" +
            "        )\n" +
            "        VALUES (\n" +
            "        #{userId,jdbcType=INTEGER},\n" +
            "        #{status,jdbcType=VARCHAR}\n" +
            "        )")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    Long insert(Order model);

    @Delete("DELETE FROM t_order WHERE order_id = #{orderId,jdbcType=INTEGER}")
    void delete(Long orderId);

    @Update("DROP TABLE IF EXISTS t_order;")
    void dropTable();
}
