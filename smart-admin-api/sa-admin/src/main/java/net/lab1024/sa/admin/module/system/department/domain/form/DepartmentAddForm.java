package net.lab1024.sa.admin.module.system.department.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 部门 添加表单
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class DepartmentAddForm {

    @ApiModelProperty("부서 이름")
    @Length(min = 1, max = 50, message = "정확한 부서명을 입력하세요(1~50자).")
    @NotNull(message = "정확한 부서명을 입력하세요(1~50자).")
    private String name;

    @ApiModelProperty("정렬 기준")
    @NotNull(message = "값으로 정렬")
    private Integer sort;

    @ApiModelProperty("부서장 아이디")
    private Long managerId;

    @ApiModelProperty("상위 부서 ID(선택 사항)")
    private Long parentId;

}
