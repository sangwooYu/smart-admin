package net.lab1024.sa.admin.module.business.oa.bank.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankVO {

    @ApiModelProperty("은행 정보 ID")
    private Long bankId;

    @ApiModelProperty("계좌 은행")
    private String bankName;

    @ApiModelProperty("계정 이름")
    private String accountName;

    @ApiModelProperty("계정 번호")
    private String accountNumber;

    @ApiModelProperty("비고")
    private String remark;

    @ApiModelProperty("대중이")
    private Boolean businessFlag;

    @ApiModelProperty("기업 ID")
    private Long enterpriseId;

    @ApiModelProperty("회사 이름")
    private String enterpriseName;

    @ApiModelProperty("비활성화 상태")
    private Boolean disabledFlag;

    @ApiModelProperty("크리에이터 ID")
    private Long createUserId;

    @ApiModelProperty("제작자 이름")
    private String createUserName;

    @ApiModelProperty("생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("업데이트 시간")
    private LocalDateTime updateTime;
}
