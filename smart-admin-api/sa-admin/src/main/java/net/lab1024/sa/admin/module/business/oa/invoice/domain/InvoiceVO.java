package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceVO {

    @ApiModelProperty("송장 정보 ID")
    private Long invoiceId;

    @ApiModelProperty("송장 수취인")
    private String invoiceHeads;

    @ApiModelProperty("납세자 식별 번호")
    private String taxpayerIdentificationNumber;

    @ApiModelProperty("은행 계좌")
    private String accountNumber;

    @ApiModelProperty("계좌 개설 은행")
    private String bankName;

    @ApiModelProperty("비고")
    private String remark;

    @ApiModelProperty("기업")
    private Long enterpriseId;

    @ApiModelProperty("회사 이름")
    private String enterpriseName;

    @ApiModelProperty("비활성화 상태")
    private Boolean disabledFlag;

    @ApiModelProperty("createUserId")
    private Long createUserId;

    @ApiModelProperty("createUserName")
    private String createUserName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;
}
