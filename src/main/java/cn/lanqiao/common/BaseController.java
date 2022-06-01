package cn.lanqiao.common;

import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.service.ISysDeptService;
import cn.lanqiao.service.ISysMenuService;
import cn.lanqiao.service.ISysRoleService;
import cn.lanqiao.service.ISysUserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基类控制器
 */
public class BaseController {

    @Resource
    protected ISysUserService userService;
    @Resource
    protected ISysDeptService deptService;
    @Resource
    protected ISysRoleService roleService;
    @Resource
    protected ISysMenuService menuService;


}