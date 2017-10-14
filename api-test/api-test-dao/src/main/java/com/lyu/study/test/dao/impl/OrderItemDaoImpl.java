/**
 * @author LYU
 * @create 2017年10月14日 8:30
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.impl;

import com.lyu.study.test.dao.OrderItemDao;
import com.lyu.study.test.dao.mapper.OrderItemMapper;
import com.lyu.study.test.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public void createIfNotExistsTable() {
        orderItemMapper.createIfNotExistsTable();
    }

    @Override
    public void truncateTable() {
        orderItemMapper.truncateTable();
    }

    @Override
    public List<OrderItem> selectAll() {
        return orderItemMapper.selectAll();
    }

    @Override
    public Long insert(OrderItem model) {
        return orderItemMapper.insert(model);
    }

    @Override
    public void delete(Long orderId) {
        orderItemMapper.delete(orderId);
    }

    @Override
    public void dropTable() {
        orderItemMapper.dropTable();
    }
}
