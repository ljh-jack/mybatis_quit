package cn.lanqiao.common;

import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.security.UserSecrity;
import cn.lanqiao.service.*;
//import cn.lanqiao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基类控制器
 */
/**
 * 基类控制器
 */
public class BaseController {
    @Resource
    protected IndexService indexService;
    @Resource
    protected ISysUserService userService;
    @Resource
    protected ISysDeptService deptService;
    @Resource
    protected ISysRoleService roleService;
    @Resource
    protected ISysMenuService menuService;
    @Resource
    protected BCryptPasswordEncoder passwordEncoder;



    /**
     * 获取登录后的用户对象
     *
     * @return Tsysoper
     */
    public UserSecrity getLoginSysUser() {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (null == obj || !(obj instanceof UserSecrity)) {
            return null;
        }
        UserSecrity user = (UserSecrity) obj;
        return user;
    }

    /**
     * 获取IP
     * @param request
     * @return
     * @throws Exception
     */
    private  String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String unknown="unknown";
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ipString) || unknown.equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || unknown.equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || unknown.equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }

        return ipString;
    }
}