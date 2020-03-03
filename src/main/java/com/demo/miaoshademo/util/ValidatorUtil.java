package com.demo.miaoshademo.util;

import com.demo.miaoshademo.common.CmsStatus;
import com.demo.miaoshademo.exception.GlobalException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            throw new GlobalException(CmsStatus.MOBILE_EMPTY);
        }
        Matcher m = mobile_pattern.matcher(src);
        if (!m.matches()) {
            throw new GlobalException(CmsStatus.MOBILE_ERROR);
        }
        return true;
    }


}
