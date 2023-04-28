package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * OA发票信息新建
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-06-23 19:32:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class InvoiceAddForm {

    @ApiModelProperty("송장 수취인")
    @NotBlank(message = "인보이스 헤더는 비워 둘 수 없습니다.")
    @Length(max = 200, message = "최대 200자의 인보이스 헤더")
    private String invoiceHeads;

    @ApiModelProperty("납세자 식별 번호")
    @NotBlank(message = "납세자 식별 번호는 비워 둘 수 없습니다.")
    @Length(max = 200, message = "납세자 식별 번호 최대 200자")
    private String taxpayerIdentificationNumber;

    @ApiModelProperty("은행 계좌")
    @NotBlank(message = "은행 계좌는 비어 있을 수 없습니다.")
    @Length(max = 200, message = "은행 계좌 최대 200자")
    private String accountNumber;

    @ApiModelProperty("계좌 개설 은행")
    @NotBlank(message = "계좌 개설 은행은 비어있을 수 없습니다.")
    @Length(max = 200, message = "은행 개설 시 최대 200자")
    private String bankName;

    @ApiModelProperty("상태 활성화")
    @NotNull(message = "사용 상태는 비어 있을 수 없습니다.")
    private Boolean disabledFlag;

    @ApiModelProperty("비고")
    @Length(max = 500, message = "최대 500자의 메모")
    private String remark;

    @ApiModelProperty("기업")
    @NotNull(message = "비즈니스는 비워둘 수 없습니다.")
    private Long enterpriseId;

    @ApiModelProperty(value = "작성자", hidden = true)
    private Long createUserId;

    @ApiModelProperty(value = "제작자 이름", hidden = true)
    private String createUserName;
}
