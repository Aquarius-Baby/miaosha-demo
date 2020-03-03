package com.demo.miaoshademo.controller;

import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class DemoController {


    @ResponseBody
    @GetMapping("/get")
    public Result test() {
        return ResultGenerator.genSuccessResult("get success");
    }

    @ResponseBody
    @PostMapping("/post")
    public Result post() {
        return ResultGenerator.genSuccessResult("post success");
    }

    @GetMapping("/fail")
    @ResponseBody
    public Result fail() {
        return ResultGenerator.genFailResult("ok");
    }

    /**
     * return view[html]
     *
     * @param request
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(HttpServletRequest request, @RequestParam(value = "name", defaultValue = "") String name) {
        request.setAttribute("name", name);
        return "hello";
    }
}
