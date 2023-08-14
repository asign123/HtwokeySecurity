package com.htwokey.htwokeysecurity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hchbo
 * @since  2023/4/17 11:08
 */
public class DateUtil {

    /**
     * 格式化时间
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
