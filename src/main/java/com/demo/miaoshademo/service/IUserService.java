package com.demo.miaoshademo.service;

import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface IUserService {
    boolean login(HttpServletResponse response, LoginVo loginVo);

    MiaoshaUser getByToken(HttpServletResponse response, String token);
    MiaoshaUser getById(long userId);
}
