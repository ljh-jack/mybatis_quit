package cn.lanqiao.vo.tree;

import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysDept;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@Data
public class TreeNodeVo {
    private String id;
    private boolean open = true;
    private String name;
    private String parentId;
    private List<TreeNodeVo> children;

    public TreeNodeVo(String id,String topId,String name){
        this.id = id;
        this.parentId = topId;
        this.name = name;
    }
    public TreeNodeVo(){}

    /**
     * 构造器
     * @param sysDeptList 资源列表
     * @param topId 树顶层节点名称 null：不手动添加顶层节点
     * @return
     */
    public static List<TreeNodeVo> convertTreeNodeVo(List<SysDept> sysDeptList, String topId){
        List<TreeNodeVo> nodeList = new ArrayList<>();
        //1、判断列表是否为空
        if (sysDeptList == null || sysDeptList.isEmpty()){
            return nodeList;
        }
        if (StringUtils.isNotEmpty(topId)){
            //遍历获取根节点
            for (SysDept sd : sysDeptList) {
                if (sd.getParentId().equals(topId)){
                    //创建根节点
                    TreeNodeVo treeNodeVo = new TreeNodeVo(sd.getId(), sd.getParentId(), sd.getDeptName());
                    //获取根节点下的子节点
                    List<TreeNodeVo> treeNodeVos = setChild(treeNodeVo,sysDeptList);
                    //将节点添加到根节点
                    treeNodeVo.setChildren(treeNodeVos);
                    nodeList.add(treeNodeVo);
                }
            }
        }
        return nodeList;
    }

    /**
     * 递归设置子节点
     * @param treeNodeVo
     * @param sysDeptList
     * @return
     */
    private static List<TreeNodeVo> setChild(TreeNodeVo treeNodeVo, List<SysDept> sysDeptList) {
        ArrayList<TreeNodeVo> nodeList = new ArrayList<>();
        for (SysDept sd : sysDeptList) {
            if (sd.getParentId().equals(treeNodeVo.getId())){
                //构造子节点
                TreeNodeVo nodeVo = new TreeNodeVo(sd.getId(), sd.getParentId(), sd.getDeptName());
                //递归设置子节点
                nodeVo.setChildren(setChild(nodeVo,sysDeptList));
                nodeList.add(nodeVo);
            }
        }
        return nodeList;
    }


}
