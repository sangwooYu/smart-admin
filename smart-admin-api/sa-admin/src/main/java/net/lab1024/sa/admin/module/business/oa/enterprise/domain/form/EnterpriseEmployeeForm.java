package net.lab1024.sa.admin.module.business.oa.enterprise.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EnterpriseEmployeeForm {

    @ApiModelProperty("회사 ID")
    @NotNull(message = "회사 ID는 비워둘 수 없습니다.")
    private Long enterpriseId;

    @ApiModelProperty("직원 정보 ID")
    @NotEmpty(message = "직원 정보 ID는 비워둘 수 없습니다.")
    private List<Long> employeeIdList;
}