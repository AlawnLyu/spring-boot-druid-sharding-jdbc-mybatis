/**
 * @author LYU
 * @create 2017年10月13日 11:26
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.impl;

import com.lyu.study.test.dao.OrderDao;
import com.lyu.study.test.dao.mapper.OrderMapper;
import com.lyu.study.test.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createIfNotExistsTable() {
        orderMapper.createIfNotExistsTable();
    }

    @Override
    public void truncateTable() {
        orderMapper.truncateTable();
    }

    @Override
    public Long insert(Order model) {
        return orderMapper.insert(model);
    }

    @Override
    public void delete(Long orderId) {
        orderMapper.delete(orderId);
    }

    @Override
    public void dropTable() {
        orderMapper.dropTable();
    }
}
