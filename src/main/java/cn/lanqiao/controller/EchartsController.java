package cn.lanqiao.controller;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/18 12:44
 */
@Controller
@RequestMapping(EchartsController.SOURCE)
public class EchartsController {
    public static final String SOURCE="/";
    @RequestMapping("toEcharts")
    public String success(ModelAndView view){
        return EchartsController.SOURCE+"echarts";
    }
    @GetMapping("epidemic")
    @ResponseBody
    public String epidemic() {
        return HttpUtil.get("https://c.m.163.com/ug/api/wuhan/app/data/list-total");
    }

}
