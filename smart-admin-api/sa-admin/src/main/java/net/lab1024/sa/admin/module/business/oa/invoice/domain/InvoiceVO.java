package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * OA发票信息
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-06-23 19:32:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
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
