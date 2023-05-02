package net.lab1024.sa.admin.module.system.role.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的菜单更新
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleMenuUpdateForm {

    /**
     * 角色id
     */
    @ApiModelProperty("역할 ID")
    @NotNull(message = "역할 ID는 비어 있을 수 없습니다.")
    private Long roleId;

    /**
     * 菜单ID 集合
     */
    @ApiModelProperty("메뉴 ID 수집")
    @NotNull(message = "메뉴 ID는 비워둘 수 없습니다.")
    private List<Long> menuIdList;

}
