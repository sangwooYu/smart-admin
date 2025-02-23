package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InvoiceUpdateForm extends InvoiceAddForm {

    @ApiModelProperty("송장 정보 ID")
    @NotNull(message = "인보이스 정보 ID는 비워 둘 수 없습니다.")
    private Long invoiceId;
}
