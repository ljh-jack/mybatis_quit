package cn.lanqiao.vo.tree;

import cn.lanqiao.entity.SysDept;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNodeVo {
    private String id;
    private boolean open=true;
    private String name;
    private String parentId;
    private List<TreeNodeVo> children;

    public TreeNodeVo(String id, String topId,String name) {
        this.id=id;
        this.parentId=topId;
        this.name=name;
    }

    public TreeNodeVo() {

    }


    /**
     * 构造树，
     * @param topId 树顶层节点名称，null：不手动添加顶层节点
     * @param sysDeptList 资源列表
     * @return
     */
    public static List<TreeNodeVo> convertTreeNodeVo(List<SysDept>  sysDeptList, String topId){
        List<TreeNodeVo> nodeList = new ArrayList<>();
//        1、判断列表是否为空
        if (sysDeptList == null || sysDeptList.isEmpty()) {
            return nodeList;
        }
        if(StringUtils.isNotEmpty(topId)){
            for (SysDept sd : sysDeptList) {
                if(sd.getParentId().equals(topId)){
                    TreeNodeVo treeNodeVo = new TreeNodeVo(sd.getParentId(), sd.getId(), sd.getDeptName());
                    List<TreeNodeVo> treeNodeVos = setChild(treeNodeVo, sysDeptList);
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
    public static List<TreeNodeVo> setChild(TreeNodeVo treeNodeVo,List<SysDept>  sysDeptList){
        List<TreeNodeVo> nodeList = new ArrayList<>();
        for(SysDept sd:sysDeptList){
            if(sd.getParentId().equals(treeNodeVo.getId())){
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
