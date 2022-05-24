package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.query.SysUserQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Controller
@RequestMapping(SysUserController.RESOURCE)
public class SysUserController extends BaseController {
    static final String RESOURCE = "sysUser";
    /**
     * 返回用户页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return SysUserController.RESOURCE+"/index";
    }

    /**
     * 分页条件查询用户
     * @param suq
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(SysUserQuery suq){
    return userService.getList(suq);
    }
    /**
     * 设置状态
     * @return
     */
    @RequestMapping("/setStatus")
    @ResponseBody
    public JsonResult setStatus(SysUser entity) {
        return userService.setStatus(entity);
    }
}


