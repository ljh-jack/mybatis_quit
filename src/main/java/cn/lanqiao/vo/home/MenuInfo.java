package cn.lanqiao.vo.home;


import cn.lanqiao.constant.MenuConstant;
import cn.lanqiao.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuInfo {
    private String id;
    private String parentId;
    private String title;
    private String icon;
    private String href;
    private String target;
    private List<MenuInfo> child;

    public MenuInfo(String id, String parentId, String menuName) {
        this.id=id;
        this.parentId=parentId;
        this.title=menuName;
    }

    public MenuInfo(String id, String parentId, String title, String icon, String href) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.icon = icon;
        this.href = href;
    }

    /**
     * 将菜单转换成树
     * @param menuList
     * @return
     */
    public static List<MenuInfo> convertMenuInfo(List<SysMenu> menuList){
        List<MenuInfo> sysMenuList = new ArrayList<>();
            for (SysMenu sysMenu : menuList) {
                if (MenuConstant.MENU_TYPE_M.equals(sysMenu.getMenuType())) {
                    //创建根节点
                    MenuInfo root = new MenuInfo(sysMenu.getId(), sysMenu.getParentId(),
                            sysMenu.getMenuName(),sysMenu.getIcon(),sysMenu.getUrl());
                    //获取根节点下的字节点
                    List<MenuInfo> menuInfos = setChild(root, menuList);
                    //将节点添加到根节点
                    root.setChild(menuInfos);
                    sysMenuList.add(root);
                }
            }
        return sysMenuList;
    }

    private static List<MenuInfo> setChild(MenuInfo menuInfo, List<SysMenu> menuList) {
        List<MenuInfo> sysMenuList = new ArrayList<>();
        for(SysMenu sysMenu:menuList){
            //如果时菜单并且时改目录的子节点
            if(sysMenu.getParentId().equals(menuInfo.getId()) && MenuConstant.MENU_TYPE_C.equals(sysMenu.getMenuType())){
                //构造子节点
                MenuInfo root = new MenuInfo(sysMenu.getId(), sysMenu.getParentId(),
                        sysMenu.getMenuName(),sysMenu.getIcon(),sysMenu.getUrl());                //递归设置子节点
                root.setChild(setChild(root,menuList));
                sysMenuList.add(root);
            }
        }
        return sysMenuList;
    }
}
