package net.lab1024.sa.admin.module.system.menu.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 简易的菜单VO
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class MenuSimpleTreeVO {

    @ApiModelProperty("메뉴 ID")
    private Long menuId;

    @ApiModelProperty("메뉴 이름")
    private String menuName;

    @ApiModelProperty("기능 포인트 관련 메뉴 ID")
    private Long contextMenuId;

    @ApiModelProperty("상위 메뉴 ID")
    private Long parentId;

    @ApiModelProperty("메뉴 유형")
    private Integer menuType;

    @ApiModelProperty("하위 메뉴")
    private List<MenuSimpleTreeVO> children;
}
