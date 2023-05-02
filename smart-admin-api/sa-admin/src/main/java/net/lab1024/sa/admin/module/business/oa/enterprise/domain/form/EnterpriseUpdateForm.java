package net.lab1024.sa.admin.module.business.oa.enterprise.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnterpriseUpdateForm extends EnterpriseCreateForm {

    @ApiModelProperty("기업 ID")
    @NotNull(message = "기업 ID는 비워둘 수 없습니다.")
    private Long enterpriseId;
}
