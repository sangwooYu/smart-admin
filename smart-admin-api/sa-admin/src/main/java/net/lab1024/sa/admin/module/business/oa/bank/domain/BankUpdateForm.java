package net.lab1024.sa.admin.module.business.oa.bank.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BankUpdateForm extends BankCreateForm {

    @ApiModelProperty("은행 정보 ID")
    @NotNull(message = "은행 정보 ID는 비워둘 수 없습니다.")
    private Long bankId;
}
