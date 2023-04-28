package net.lab1024.sa.admin.module.system.role.domain.form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色 添加表单
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2022-02-26 19:09:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleAddForm {

    /**
     * 角色名称
     */
    @ApiModelProperty("캐릭터 이름")
    @NotNull(message = "캐릭터 이름은 비워둘 수 없습니다.")
    @Length(min = 1, max = 20, message = "캐릭터 이름(1~20자)")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty("캐릭터 설명")
    @Length(max = 255, message = "캐릭터 설명 최대 255자")
    private String remark;


}
