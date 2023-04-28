package net.lab1024.sa.admin.module.business.oa.invoice.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * OA发票信息查询
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-06-23 19:32:59
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class InvoiceQueryForm extends PageParam {

    @ApiModelProperty("기업 ID")
    private Long enterpriseId;

    @ApiModelProperty("키워드")
    @Length(max = 200, message = "키워드 최대 200자")
    private String keywords;

    @ApiModelProperty("시작 시간")
    private LocalDate startTime;

    @ApiModelProperty("마감 시간")
    private LocalDate endTime;

    @ApiModelProperty("비활성화 상태")
    private Boolean disabledFlag;

    @ApiModelProperty(value = "상태 삭제", hidden = true)
    private Boolean deletedFlag;
}
