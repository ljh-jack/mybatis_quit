package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.entity.UserEntity;
import cn.lanqiao.query.SysUserQuery;
import cn.lanqiao.utils.ExcelUtil;
import cn.lanqiao.vo.UserRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Controller
@RequestMapping(SysUserController.SOURCE)
public class SysUserController extends BaseController {

    public static final String SOURCE = "sysUser";

    @RequestMapping("/toIndex")
    @PreAuthorize("hasAuthority('/sysUser/toIndex')")
    public String toIndex() {
        return SysUserController.SOURCE + "/index";
    }

    @RequestMapping("/addUI")
    public String addUI() {
        return SysUserController.SOURCE + "/add";
    }

    /**
     * 添加用户页面
     * @return
     * */
    @PreAuthorize("hasAuthority('/sysUser/updateSysUser')")
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        UserRoleVo entity = (UserRoleVo) userService.getInfo(id);
        view.addObject("info", entity);
        view.setViewName(SysUserController.SOURCE + "/edit");
        return view;
    }

    /**
     * 查询用户
     *
     * @param query
     * @return
     */
    @PreAuthorize("hasAuthority('/sysUser/findSysUserPage')")
    @ResponseBody //回传json数据
    @RequestMapping("/list")
    public JsonResult list(SysUserQuery query) {
        return userService.getList(query);
    }

    /**
     * 添加用户
     * @param entity 实体对象
     * @return
     * */
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('/sysUser/updateSysUser','/sysUser/saveSysUser')")
    public JsonResult add(@RequestBody SysUserQuery entity) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(entity,sysUser);
        return userService.edit(sysUser,entity.getRoleIds());
    }

    /**
     * 根据id删除用户
     * @param ids
     * @return
     */
    @PreAuthorize("hasAuthority('/sysUser/deleteSysUser')")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult deleteUser(String ids) {
        return userService.deleteByIds(ids);
    }

    /**
     * 修改状态
     *
     * @param sysUser
     * @return
     */
    @PreAuthorize("hasAuthority('/sysUser/updateSysUser')")
    @RequestMapping("/setStatus")
    @ResponseBody
    public JsonResult setStatus(SysUser sysUser) {
        return userService.setStatus(sysUser);
    }
    @RequestMapping("/uploadUsers")
    @ResponseBody
    public JsonResult uploadUsers(MultipartFile file){
        return  userService.uploadUsers(file);
    }
    @RequestMapping("/downLoadUsers")
    public JsonResult downLoadUsers(){
        return null;
    }
}

