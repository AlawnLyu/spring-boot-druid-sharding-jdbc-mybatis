/**
 * @author LYU
 * @create 2017年10月19日 8:36
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.dao.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class PreciseModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    /**
     * @author: LYU
     * @description:
     * @method: doSharding
     * @param availableTargetNames
     * @param shardingValue
     * @return: java.lang.String
     * @date: 2017年10月20日 10:56:33
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
