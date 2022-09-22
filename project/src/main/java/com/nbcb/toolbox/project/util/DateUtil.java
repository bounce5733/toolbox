package com.nbcb.toolbox.project.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 *
 * @Author jiangyonghua
 * @Date 2022/9/20 09:15
 * @Version 1.0
 **/
public class DateUtil {

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String today() {
        Calendar cal = Calendar.getInstance();
        return DateUtil.formatDate(cal.getTime(), DATE_FORMAT);
    }
}
