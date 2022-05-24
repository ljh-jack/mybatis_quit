package cn.lanqiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/5/23 19:43
 */
public class UserConstant {
    /**
     * 性别
     */
    public static Map<String, String> USER_SEX_LIST = new HashMap<String, String>() {
        {
            put("1", "男");
            put("2", "女");
            put("3", "未知");
        }
    };
    /**
     * 状态
     */
    public static Map<String, String> USER_STATUS_LIST = new HashMap<String, String>() {
        {
            put("1", "正常");
            put("2", "禁用");
        }
    };
    /**
     * 用户类型
     */
    public static Map<String, String> USER_TYPE_LIST = new HashMap<String, String>() {
        {
            put("1", "系统用户");
            put("2", "普通用户");
        }
    };
}
