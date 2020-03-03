package com.demo.miaoshademo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.common.CmsStatus;
import com.demo.miaoshademo.common.Constant;
import com.demo.miaoshademo.dao.UserMapper;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.exception.GlobalException;
import com.demo.miaoshademo.redis.KeyMiaoshaUser;
import com.demo.miaoshademo.service.IUserService;
import com.demo.miaoshademo.util.MD5Utils;
import com.demo.miaoshademo.util.UUIDUtil;
import com.demo.miaoshademo.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CmsStatus.SYSTEM_ERROR);
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getByMobile(mobile);
        if (user == null) {
            throw new GlobalException(CmsStatus.MOBILE_NOT_EXIST);
        }

        // 密码验证
        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Utils.formPassToDBPass(password, saltDb);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CmsStatus.PASSWORD_ERROR);
        }
        //生成cookie 将session返回游览器 分布式session
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String realKey = KeyMiaoshaUser.getByToken(token).getRealKey();
        String jsonString = redisTemplate.opsForValue().get(realKey);
        MiaoshaUser user = JSONObject.parseObject(jsonString, MiaoshaUser.class);
        // 增加过期时间
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        KeyMiaoshaUser keyMiaoshaUser = KeyMiaoshaUser.getByToken(token);
        String realKey = keyMiaoshaUser.getRealKey();
        redisTemplate.opsForValue().set(realKey, JSONObject.toJSONString(user), keyMiaoshaUser.expireSeconds(), TimeUnit.SECONDS);
        Cookie cookie = new Cookie(Constant.COOKIE_NAME_TOKEN, token);
        //设置有效期
        cookie.setMaxAge(keyMiaoshaUser.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public MiaoshaUser getById(long userId) {
        return getByMobile(String.valueOf(userId));
    }

    public MiaoshaUser getByMobile(String mobile) {
        //取缓存
        String realKey = KeyMiaoshaUser.getByToken(mobile).getRealKey();
        String redisStr = redisTemplate.opsForValue().get(realKey);
        MiaoshaUser user = JSONObject.parseObject(redisStr, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = userMapper.getByMobile(mobile);
        if (user != null) {
            redisTemplate.opsForValue().set(realKey, JSONObject.toJSONString(user));
        }
        return user;
    }

}
