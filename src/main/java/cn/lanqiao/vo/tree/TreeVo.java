package cn.lanqiao.vo.tree;

import cn.lanqiao.entity.SysMenu;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class TreeVo {
    private String value; //值
    private String name; //名字
    private String parentId;
    private boolean selected= false; //默认选中
    private boolean  disabled= false; //选中不可更
    private List<TreeVo> children;

    public TreeVo(String value,String parentId, String name) {
        this.value = value;
        this.name = name;
        this.parentId = parentId;
    }

    public TreeVo() {

    }


    /**
     * 构造树，
     * @param topId 树顶层节点名称，null：不手动添加顶层节点
     * @param sysMenuList 资源列表
     * @return
     */
    public static List<TreeVo> convertTreeVo(List<SysMenu>  sysMenuList, String topId){
        if(StringUtils.isEmpty(topId)){
            topId="0";
        }
        List<TreeVo> nodeList = new ArrayList<>();
//        1、判断列表是否为空
        if (sysMenuList == null || sysMenuList.isEmpty()) {
            return nodeList;
        }
        if(StringUtils.isNotEmpty(topId)){
            //遍历获取根节点
            for(SysMenu sd:sysMenuList){
                if(sd.getParentId().equals(topId)){
                    //创建根节点
                    TreeVo treeVo = new TreeVo(sd.getId(), sd.getParentId(), sd.getMenuName());
                    //获取根节点下的字节点
                    List<TreeVo> TreeVos = setChild(treeVo, sysMenuList);
                    //将节点添加到根节点
                    treeVo.setChildren(TreeVos);
                    nodeList.add(treeVo);

                }
            }
        }
        return nodeList;
    }

    /**
     * 返回回显权限菜单
     * @param sysMenuList  权限列表
     * @param topId  根节点
     * @param roleMenus  角色拥有的权限
     * @return
     */
    public static List<TreeVo> convertTreeVo(List<SysMenu> sysMenuList,  String topId, List<String> roleMenus) {
        if (Objects.isNull(sysMenuList)) {
            return convertTreeVo(sysMenuList,topId);
        }
        if(StringUtils.isEmpty(topId)){
            topId="0";
        }
        List<TreeVo> nodeList = new ArrayList<>();
        String finalTopId = topId;
        sysMenuList.forEach(s -> {
            if (String.valueOf(s.getParentId()).equals(finalTopId)) {
                TreeVo treeVo = new TreeVo(String.valueOf(s.getId())
                        , String.valueOf(s.getParentId()), s.getMenuName());
                treeVo.setChildren(setChild(treeVo,sysMenuList));
                List<TreeVo> result = treeVo.getChildren();
                if (!result.isEmpty()) {
                    for (TreeVo tVo : result) {
                        List<TreeVo> children = tVo.getChildren();
                        int count = children.size();
                        for (TreeVo child : children) {
                            if (roleMenus.contains(child.getValue())) {
                                child.setSelected(true);
                                count--;
                            }
                        }
                        if (!children.isEmpty() && count == 0) {
                            tVo.setSelected(true);
                        }
                    }
                }
                nodeList.add(treeVo);
            }
        });
        return nodeList;
    }
    /**
     * 递归设置子节点
     * @param treeVo
     * @param sysMenuList
     * @return
     */
    public static List<TreeVo> setChild(TreeVo treeVo,List<SysMenu>  sysMenuList, List<String> roleMenus){
        List<TreeVo> nodeList = new ArrayList<>();
        for(SysMenu sd:sysMenuList){
            if(sd.getParentId().equals(treeVo.getValue())){
                //构造子节点
                TreeVo nodeVo = new TreeVo(sd.getId(), sd.getParentId(), sd.getMenuName());
                if(!roleMenus.contains(nodeVo.getValue())){
                    treeVo.setSelected(false);
                }
                //递归设置子节点
                nodeVo.setChildren(setChild(nodeVo,sysMenuList,roleMenus));
                nodeList.add(nodeVo);
            }
        }
        return nodeList;
    }

    /**
     * 递归设置子节点
     * @param treeVo
     * @param sysMenuList
     * @return
     */
    public static List<TreeVo> setChild(TreeVo treeVo,List<SysMenu>  sysMenuList){
        List<TreeVo> nodeList = new ArrayList<>();
        for(SysMenu sd:sysMenuList){
            if(sd.getParentId().equals(treeVo.getValue())){
                //构造子节点
                TreeVo nodeVo = new TreeVo(sd.getId(), sd.getParentId(), sd.getMenuName());
                //递归设置子节点
                nodeVo.setChildren(setChild(nodeVo,sysMenuList));
                nodeList.add(nodeVo);
            }
        }
        return nodeList;
    }

}
