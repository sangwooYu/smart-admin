package net.lab1024.sa.admin.module.system.employee.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.enumeration.GenderEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工信息
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2021-12-21 23:05:56
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Data
public class EmployeeVO {

    @ApiModelProperty("기본 키 ID")
    private Long employeeId;

    @ApiModelProperty("로그인 계정")
    private String loginName;

    @ApiModelPropertyEnum(GenderEnum.class)
    private Integer gender;

    @ApiModelProperty("직원 이름")
    private String actualName;

    @ApiModelProperty("휴대폰 번호")
    private String phone;

    @ApiModelProperty("부서 ID")
    private Long departmentId;

    @ApiModelProperty("비활성화 여부")
    private Boolean disabledFlag;

    @ApiModelProperty("예 또는 아니요 슈퍼 관리자")
    private Boolean administratorFlag;

    @ApiModelProperty("부서 이름")
    private String departmentName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("역할 목록")
    private List<Long> roleIdList;

    @ApiModelProperty("역할 이름 목록")
    private List<String> roleNameList;
}
