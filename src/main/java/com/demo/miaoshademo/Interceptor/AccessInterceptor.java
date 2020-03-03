package com.demo.miaoshademo.Interceptor;

import com.demo.miaoshademo.common.*;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.redis.KeyAccess;
import com.demo.miaoshademo.service.IMiaoshaService;
import com.demo.miaoshademo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);

    @Autowired
    IMiaoshaService miaoshaService;

    @Autowired
    IUserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /**
         * 对请求进行拦截 需在 webConfig中添加拦截配置
         *
         */
        if (handler instanceof HandlerMethod) {
            logger.info("打印拦截方法handler ：{} ", handler);
            HandlerMethod hm = (HandlerMethod) handler;
            MiaoshaUser user = getUser(request, response);
            UserContext.setUser(user);
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    remindLogin(response, CmsStatus.USER_SHOULD_LOGIN.getMessage());
                    return false;
                }
                key += "_" + user.getNickname();
            }
            KeyAccess ak = KeyAccess.getById(seconds, key);
            String redisCountStr = redisTemplate.opsForValue().get(ak.getRealKey());
            if (redisCountStr == null) {
                redisTemplate.opsForValue().set(ak.getRealKey(), String.valueOf(1), ak.expireSeconds(), TimeUnit.SECONDS);
            } else if (Integer.valueOf(redisCountStr) < maxCount) {
                redisTemplate.opsForValue().increment(ak.getRealKey());
            } else {
                remindLogin(response, CmsStatus.REQUEST_ILLEGAL.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserContext.removeUser();
    }


    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(Constant.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, Constant.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void remindLogin(HttpServletResponse response, String msg) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(ResultGenerator.genFailResult(msg));
        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


}
