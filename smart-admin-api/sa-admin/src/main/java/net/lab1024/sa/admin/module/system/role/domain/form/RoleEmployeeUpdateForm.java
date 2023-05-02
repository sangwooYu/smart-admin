package net.lab1024.sa.admin.module.system.role.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的员工更新
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleEmployeeUpdateForm {

    @ApiModelProperty("역할 ID")
    @NotNull(message = "역할 ID는 비어 있을 수 없습니다.")
    protected Long roleId;

    @ApiModelProperty(value = "직원 ID 수집")
    @NotEmpty(message = "직원 ID는 비워둘 수 없습니다.")
    protected List<Long> employeeIdList;

}
