/**
 * @author LYU
 * @create 2017-10-13-11:13
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao;

import com.lyu.study.test.entity.Order;

public interface OrderDao {
    void createIfNotExistsTable();

    void truncateTable();

    Long insert(Order model);

    void delete(Long orderId);

    void dropTable();
}
