package cn.lanqiao.constant;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class UserConstant {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    public static  String DEFAULT_PWD  ="$10$tg4UtlQHE4P/Du7ZcNZkae4Tahc56/Qx06Ho66JHtrLx04ZwJUD6m";

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