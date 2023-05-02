package net.lab1024.sa.admin.module.business.oa.enterprise.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EnterpriseEmployeeVO {

    private Long enterpriseEmployeeId;

    @ApiModelProperty("기업 ID")
    private Long enterpriseId;

    @ApiModelProperty("회사 이름")
    private String enterpriseName;

    @ApiModelProperty("직원ID")
    private Long employeeId;

    @ApiModelProperty("로그인 계정")
    private String loginName;

    @ApiModelProperty("직원 이름")
    private String actualName;

    @ApiModelProperty("휴대폰 번호")
    private String phone;

    @ApiModelProperty("부서 ID")
    private Long departmentId;

    @ApiModelProperty("비활성화 여부")
    private Boolean disabledFlag;

    @ApiModelProperty("부서 이름")
    private String departmentName;

}
