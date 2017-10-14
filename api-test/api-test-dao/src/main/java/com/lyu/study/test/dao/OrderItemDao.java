/**
 * @author LYU
 * @create 2017-10-14-8:28
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao;

import com.lyu.study.test.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void createIfNotExistsTable();

    void truncateTable();

    List<OrderItem> selectAll();

    Long insert(OrderItem model);

    void delete(Long orderId);

    void dropTable();
}
