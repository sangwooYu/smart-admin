package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通知公告 类型
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeTypeVO {

    @ApiModelProperty("알림 유형 ID")
    private Long noticeTypeId;

    @ApiModelProperty("통지 유형 - 이름")
    private String noticeTypeName;

}
