package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.query.SysUserQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Controller
@RequestMapping(SysUserController.SOURCE)
public class SysUserController extends BaseController {
    static final String SOURCE = "sysUser";
    /**
     * 返回用户页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return SysUserController.SOURCE+"/index";
    }
    /**
     * 添加用户页面
     * @return
     */
    @RequestMapping("/addUI")
    public String addUI() {
        return SysUserController.SOURCE+"/add";
    }
    /**
     * 添加用户
     5、SysUserServiceImpl
     * @param entity 实体对象
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody SysUser entity) {
        return userService.edit(entity);
    }

    /**
     * 编辑用户页面
     * @return
     */
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysUser sysUser =(SysUser)userService.getInfo(id);
        view.addObject("info",sysUser);
        view.setViewName(SysUserController.SOURCE+"/edit");
        return view ;
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
    /**
     * 按id删除用户
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(String ids) {
        return userService.deleteByIds(ids);
    }
}


