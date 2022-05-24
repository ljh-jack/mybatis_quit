
package cn.lanqiao.common.utils;


import java.time.LocalDateTime;

/**
 * 时间工具类
 */
public final class DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

}
