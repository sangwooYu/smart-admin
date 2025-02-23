package net.lab1024.sa.admin.module.system.role.domain.form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色更新修改
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2022-02-26 19:09:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleUpdateForm extends RoleAddForm {

    /**
     * 角色id
     */
    @ApiModelProperty("역할 ID")
    @NotNull(message = "역할 ID는 비어 있을 수 없습니다.")
    protected Long roleId;


}
