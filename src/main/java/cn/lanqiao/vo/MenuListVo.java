package cn.lanqiao.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lin
 * @date 2022/6/6 下午 5:37
 * @description: TODO
 */
@Data
public class MenuListVo {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */

    private String parentId;

    /**
     * 显示顺序
     */

    private String orderNum;

    /**
     * 请求地址
     */

    private String url;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */

    private String menuType;

    /**
     * 菜单图标
     */

    private String icon;

    /**
     * 创建者
     */

    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")

    private LocalDateTime createTime;

    /**
     * 更新者
     */

    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")

    private LocalDateTime updateTime;

    /**
     * 备注
     */

    private String remark;
}
