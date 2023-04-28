package net.lab1024.sa.admin.module.system.menu.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.system.menu.domain.form.MenuBaseForm;

import java.time.LocalDateTime;

/**
 * 菜单
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class MenuVO extends MenuBaseForm {

    @ApiModelProperty("메뉴 ID")
    private Long menuId;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("작성자")
    private Long createUserId;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;

    @ApiModelProperty("업데이트 유저 ID")
    private Long updateUserId;
}
