package net.lab1024.sa.admin.module.system.department.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class DepartmentVO {

    @ApiModelProperty("부서 ID")
    private Long departmentId;

    @ApiModelProperty("부서 이름")
    private String name;

    @ApiModelProperty("부서장 이름")
    private String managerName;

    @ApiModelProperty("부서장 아이디")
    private Long managerId;

    @ApiModelProperty("상위 부서 ID")
    private Long parentId;

    @ApiModelProperty("정렬 기준")
    private Integer sort;

}
