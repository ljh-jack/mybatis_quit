package cn.lanqiao.constant;

import java.util.HashMap;
import java.util.Map;

public class MenuConstant {
    /**
     * 目录 菜单  按钮
     */
    public static  String MENU_TYPE_M="M";
    public static  String MENU_TYPE_C="C";
    public static  String MENU_TYPE_F="F";

    /**
     * 性别
     */
    public static Map<String, String> MENU_TYPE = new HashMap<String, String>() {
        {
            put("M", "目录");
            put("C", "菜单");
            put("F", "按钮");
        }
    };
}
