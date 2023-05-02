package net.lab1024.sa.admin.module.system.role.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleVO {

    @ApiModelProperty("역할 ID")
    private Long roleId;

    @ApiModelProperty("역할 이름")
    private String roleName;

    @ApiModelProperty("역할 비고")
    private String remark;
}
