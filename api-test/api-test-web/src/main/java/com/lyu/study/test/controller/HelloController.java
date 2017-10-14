/**
 * @author LYU
 * @create 2017年10月09日 14:32
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.controller;

import com.lyu.study.test.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MyService myService;

    @RequestMapping("/first")
    public String helloWorld(String name) {
        return "hello world!" + myService.optName(name);
    }

    @RequestMapping("/testData")
    public String testData(){
        myService.createTable();
        myService.truncateTable();
        return myService.insertData().toString();
    }
}
