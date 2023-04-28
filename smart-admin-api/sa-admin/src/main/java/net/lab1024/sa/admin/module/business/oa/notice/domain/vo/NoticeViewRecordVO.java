package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeViewRecordVO {

    @ApiModelProperty("직원 ID")
    private Long employeeId;

    @ApiModelProperty("직원 이름")
    private String employeeName;

    @ApiModelProperty("직원 부서 이름")
    private String departmentName;

    @ApiModelProperty("시간 보기")
    private Integer pageViewCount;

    @ApiModelProperty("최초 IP")
    private String firstIp;

    @ApiModelProperty("최초 사용자 장비 등 식별")
    private String firstUserAgent;

    @ApiModelProperty("첫 조회 시간")
    private LocalDateTime createTime;

    @ApiModelProperty("마지막 IP")
    private String lastIp;

    @ApiModelProperty("마지막 사용자 장비 등 식별")
    private String lastUserAgent;

    @ApiModelProperty("마지막으로 본 시간")
    private LocalDateTime updateTime;
}
