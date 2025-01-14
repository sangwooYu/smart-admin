package net.lab1024.sa.admin.module.system.role.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuSimpleTreeVO;

import java.util.List;

/**
 * 角色菜单树
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleMenuTreeVO {

    @ApiModelProperty("역할 ID")
    private Long roleId;

    @ApiModelProperty("메뉴 목록")
    private List<MenuSimpleTreeVO> menuTreeList;

    @ApiModelProperty("선택한 메뉴 ID")
    private List<Long> selectedMenuId;
}
