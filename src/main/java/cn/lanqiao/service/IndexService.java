package cn.lanqiao.service;

import java.security.Principal;
import java.util.Map;

public interface IndexService {
    /**
     * 首页面菜单显示
     * @return
     */
    Map<String,Object> getIndexInfo();

    boolean updatePwd(String oldPassword, String newPassword);
}
