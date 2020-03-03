package com.demo.miaoshademo.controller;


import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.service.IUserService;
import com.demo.miaoshademo.util.MD5Util;
import com.demo.miaoshademo.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @ResponseBody
    @PostMapping("/do_login")
    public Result dologin(HttpServletResponse response, @RequestBody @Valid LoginVo loginVo) {
        loginVo.setPassword(MD5Util.inputPassToFormPass(loginVo.getPassword()));
        userService.login(response, loginVo);
        return ResultGenerator.genSuccessResult();
    }


//    @RequestMapping("/create_token")
//    @ResponseBody
//    public String createToken(HttpServletResponse response, @Valid LoginVo loginVo) {
//        logger.info(loginVo.toString());
//        String token = userService.createToken(response, loginVo);
//        return token;
//    }
}
