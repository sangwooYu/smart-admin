package net.lab1024.sa.admin.module.system.role.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的数据范围更新
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class RoleDataScopeUpdateForm {

    @ApiModelProperty("역할 ID")
    @NotNull(message = "역할 ID는 비어 있을 수 없습니다.")
    private Long roleId;

    @ApiModelProperty("설정 정보")
    @Valid
    private List<RoleUpdateDataScopeListFormItem> dataScopeItemList;


    @Data
    public static class RoleUpdateDataScopeListFormItem {

        @ApiModelProperty("데이터 범위 유형")
        @NotNull(message = "데이터 범위 유형은 비어 있을 수 없습니다.")
        private Integer dataScopeType;

        @ApiModelProperty("가시 범위")
        @NotNull(message = "가시 범위는 비워둘 수 없습니다.")
        private Integer viewType;
    }

}
