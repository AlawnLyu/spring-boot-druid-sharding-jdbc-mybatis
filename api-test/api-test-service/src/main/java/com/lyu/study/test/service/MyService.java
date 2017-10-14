/**
 * @author LYU
 * @create 2017-10-09-14:53
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.service;

import java.util.List;

public interface MyService {
    String optName(String name);

    void truncateTable();

    void createTable();

    List<Long> insertData();

    void deleteData(List<Long> orderIds);

    void dropTable();
}
