package cn.lanqiao.aop;
import cn.lanqiao.common.BaseController;
import cn.lanqiao.constant.LoginConstant;
import cn.lanqiao.constant.MenuConstant;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.security.UserSecrity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class AuthAspect extends BaseController {
    public AuthAspect() {
        System.out.println();
    }

    /**
     *@description 以controller包下的Index结尾的方法作为切入点
     *@param
     *@return
     */


//    @Pointcut("execution(public * cn.lanqiao.controller..*.*Index(..)) || execution(public * cn.sunsharp.country.resource..*.*toIndexManager(..))")
    @Pointcut("execution(public * cn.lanqiao.controller..*.*toIndex(..)) ")
    public void webLog(){
        System.out.println("xxx");
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //登陆用户
        UserSecrity user = getLoginSysUser();
        if(user==null){
            return;
        }
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI().toString();
        //获取用户所有的权限
        List<GrantedAuthority> grantedAuthorities = user.getGrantedAuthorities();
        //根据url和用户信息获取用户关联的下级资源
        List<SysMenu> userMethod = menuService.findMenuByParent(MenuConstant.MENU_TYPE_F,url);
        //用户当前的权限菜单
        List<String> menus = null;
        if(grantedAuthorities!=null&&grantedAuthorities.size()>0){
            menus = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }

        StringBuilder builder = new StringBuilder();
        if(menus!=null&&menus.size()>0){
            for(SysMenu sys:userMethod){
                String s = sys.getUrl();
                Boolean isTrue = menus!=null&&menus.contains(s);
                int index=s.lastIndexOf("/");
                if(index!=-1){
                    s=s.substring(index+1);
                }
                if(isTrue){
                    builder.append("$('#"+s+"').show();");
                    builder.append("$('."+s+"').show();");
//                    builder.append("$('#"+s+"').hide();");
//                    builder.append("$('."+s+"').hide();");
                }else{
                    builder.append("$('#"+s+"').hide();");
                    builder.append("$('."+s+"').hide();");
                }
            }
        }
        request.getSession().setAttribute(LoginConstant.MENU_PERMISSION, builder.toString());
    }
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
//        logger.info("RESPONSE : " + ret);
    }
}