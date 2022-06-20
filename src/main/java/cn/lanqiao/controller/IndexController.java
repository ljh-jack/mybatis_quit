package cn.lanqiao.controller;

import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.UpdateUtils;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysUserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(IndexController.SOURCE)
public class IndexController extends BaseController {
    public static final String SOURCE="/";

    /**
     * 返回首页
     * @param view
     * @return
     */
    @RequestMapping("toIndex")
    public String success(ModelAndView view){
        return IndexController.SOURCE+"index";
    }

    /**
     *
     * @return
     */
    @RequestMapping("getIndexInfo")
    @ResponseBody
    public Map<String, Object> getIndexInfo(){
        Map<String,Object> indexMenu = indexService.getIndexInfo();
        return indexMenu;
    }

    /**
     * 登录页面
     * @param view
     * @return
     */
    @RequestMapping("toLogin")
    public String login(ModelAndView view){
        return IndexController.SOURCE+"login";
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public JsonResult imgUpload(@RequestParam("file") MultipartFile file) {
        JsonResult j = new JsonResult();
        String fileName = UpdateUtils.upload(file);
        return j.success("上传成功",fileName);
    }
    @RequestMapping("/toPwdUI")
    public String topwd() {
        return IndexController.SOURCE + "user-password";
    }
    @RequestMapping("/toUserInfo")
    public String toUserInfo() {
        return IndexController.SOURCE + "user-setting";
    }
    @RequestMapping("/updatePwd")
    @ResponseBody
    public boolean updatePwd(String oldPassword,String newPassword) {
      return  indexService.updatePwd(oldPassword,newPassword);
    }
}
