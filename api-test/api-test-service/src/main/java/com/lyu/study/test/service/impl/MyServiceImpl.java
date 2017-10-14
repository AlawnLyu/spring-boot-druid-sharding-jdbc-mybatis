/**
 * @author LYU
 * @create 2017年10月09日 14:54
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.service.impl;

import com.lyu.study.test.dao.OrderDao;
import com.lyu.study.test.dao.OrderItemDao;
import com.lyu.study.test.entity.Order;
import com.lyu.study.test.entity.OrderItem;
import com.lyu.study.test.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    /**
     * @author: LYU
     * @description:
     * @method: optName
     * @param name
     * @return: java.lang.String
     * @date: 2017年10月09日 15:53:52
     */
    @Override
    public String optName(String name) {
        return name + "MyServiceImpl";
    }

    @Override
    public void truncateTable() {
        orderDao.truncateTable();
        orderItemDao.truncateTable();
    }

    @Override
    public void createTable() {
        orderDao.createIfNotExistsTable();
        orderItemDao.createIfNotExistsTable();
    }

    @Override
    public List<Long> insertData() {
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 2; j++) {
                Order order = new Order();
                order.setUserId(51+j);
                order.setStatus("INSERT_TEST");
                orderDao.insert(order);
                long orderId = order.getOrderId();
                orderIds.add(orderId);

                OrderItem item = new OrderItem();
                item.setOrderId(orderId);
                item.setUserId(51+j);
                item.setStatus("INSERT_TEST");
                orderItemDao.insert(item);
            }
        }
        System.out.println(orderItemDao.selectAll());
        return orderIds;
    }

    @Override
    public void deleteData(List<Long> orderIds) {
        System.out.println("2.Delete--------------");
        for (Long each : orderIds) {
            orderDao.delete(each);
            orderItemDao.delete(each);
        }
        System.out.println(orderItemDao.selectAll());
    }

    @Override
    public void dropTable() {
        orderItemDao.dropTable();
        orderDao.dropTable();
    }
}
