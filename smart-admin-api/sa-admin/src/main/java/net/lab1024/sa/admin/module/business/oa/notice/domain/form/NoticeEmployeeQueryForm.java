package net.lab1024.sa.admin.module.business.oa.notice.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

import java.time.LocalDate;

/**
 * 通知公告 员工查询表单
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeEmployeeQueryForm extends PageParam {

    @ApiModelProperty("제목, 작성자, 출처, 문서 번호")
    private String keywords;

    @ApiModelProperty("분류")
    private Long noticeTypeId;

    @ApiModelProperty("릴리스 - 시작 시간")
    private LocalDate publishTimeBegin;

    @ApiModelProperty("읽지 않은 로고")
    private Boolean notViewFlag;

    @ApiModelProperty("릴리스 - 마감일")
    private LocalDate publishTimeEnd;
}
