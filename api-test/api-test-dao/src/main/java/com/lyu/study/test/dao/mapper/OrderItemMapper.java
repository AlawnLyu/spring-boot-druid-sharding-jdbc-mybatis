/**
 * @author LYU
 * @create 2017-10-14-8:24
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.mapper;

import com.lyu.study.test.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Update("CREATE TABLE IF NOT EXISTS t_order_item (order_item_id BIGINT AUTO_INCREMENT, order_id BIGINT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_item_id));")
    void createIfNotExistsTable();

    @Update("TRUNCATE TABLE t_order_item;")
    void truncateTable();

    @Select("SELECT\n" +
            "        i.*\n" +
            "        FROM\n" +
            "        t_order o, t_order_item i\n" +
            "        WHERE\n" +
            "        o.order_id = i.order_id")
    List<OrderItem> selectAll();

    @Insert("INSERT INTO t_order_item (\n" +
            "          order_id, user_id, status\n" +
            "        )\n" +
            "        VALUES (\n" +
            "        #{orderId,jdbcType=INTEGER},\n" +
            "        #{userId,jdbcType=INTEGER},\n" +
            "        #{status,jdbcType=VARCHAR}\n" +
            "        )")
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId")
    Long insert(OrderItem model);

    @Delete("DELETE FROM t_order_item WHERE order_id = #{orderId,jdbcType=INTEGER}")
    void delete(Long orderId);

    @Update("DROP TABLE IF EXISTS t_order_item;")
    void dropTable();
}
