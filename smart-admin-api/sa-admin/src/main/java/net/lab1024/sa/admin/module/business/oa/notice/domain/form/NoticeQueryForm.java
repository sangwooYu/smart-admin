package net.lab1024.sa.admin.module.business.oa.notice.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

import java.time.LocalDate;

/**
 * 通知公告 管理查询表单
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeQueryForm extends PageParam {

    @ApiModelProperty("분류")
    private Long noticeTypeId;

    @ApiModelProperty("제목, 저자, 출처")
    private String keywords;

    @ApiModelProperty("문서 번호")
    private String documentNumber;

    @ApiModelProperty("작성자")
    private Long createUserId;

    @ApiModelProperty("로고 삭제")
    private Boolean deletedFlag;

    @ApiModelProperty("만들기 - 시작 시간")
    private LocalDate createTimeBegin;

    @ApiModelProperty("만들기 - 마감일")
    private LocalDate createTimeEnd;

    @ApiModelProperty("릴리스 - 시작 시간")
    private LocalDate publishTimeBegin;

    @ApiModelProperty("릴리스 - 마감일")
    private LocalDate publishTimeEnd;

}
