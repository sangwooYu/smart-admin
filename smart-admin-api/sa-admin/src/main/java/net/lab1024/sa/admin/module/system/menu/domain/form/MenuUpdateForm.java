package net.lab1024.sa.admin.module.system.menu.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 菜单 更新Form
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class MenuUpdateForm extends MenuBaseForm {

    @ApiModelProperty("메뉴 ID")
    @NotNull(message = "메뉴 ID는 비워둘 수 없습니다.")
    private Long menuId;

    @ApiModelProperty(hidden = true)
    private Long updateUserId;
}
