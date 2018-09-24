package com.jayzero.games.esportsclub.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomePageController
 *
 * @author 成至 forrestzhu.zl@alibaba-inc.com
 * @version HomePageController.java, v0.1
 * @date 2018/09/24
 */
@RestController
@RequestMapping("/home")
@Api(tags = "[电竞俱乐部首页]")
public class HomePageController {

    @ApiOperation(value = "SayHello", httpMethod = "GET")
    @GetMapping("/say_hello")
    public String sayHello() {
        return "hello, world.";
    }

}
