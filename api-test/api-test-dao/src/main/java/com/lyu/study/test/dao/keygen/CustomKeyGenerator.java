/**
 * @author LYU
 * @create 2017年10月21日 13:45
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.keygen;

import io.shardingjdbc.core.keygen.KeyGenerator;
import io.shardingjdbc.core.keygen.TimeService;

public class CustomKeyGenerator implements KeyGenerator {

    private TimeService timeService = new TimeService();

    @Override
    public Number generateKey() {
        Long currentMillis = timeService.getCurrentMillis();
        return currentMillis;
    }
}
