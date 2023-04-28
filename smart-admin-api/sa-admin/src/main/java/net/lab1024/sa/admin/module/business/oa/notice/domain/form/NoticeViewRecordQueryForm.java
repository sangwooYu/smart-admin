package net.lab1024.sa.admin.module.business.oa.notice.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

import javax.validation.constraints.NotNull;

/**
 * 通知公告 阅读记录查询
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeViewRecordQueryForm extends PageParam {

    @ApiModelProperty("알림 ID")
    @NotNull(message = "알림 ID는 비워둘 수 없습니다.")
    private Long noticeId;

    @ApiModelProperty("부서 ID")
    private Long departmentId;

    @ApiModelProperty("핵심 단어")
    private String keywords;


}
