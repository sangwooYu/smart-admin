package net.lab1024.sa.admin.module.business.oa.enterprise.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * OA企业模块分页查询
 *
 * @Author 1024创新实验室: 开云
 * @Date 2022/7/28 20:37:15
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class EnterpriseQueryForm extends PageParam {

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
