package net.lab1024.sa.admin.module.business.oa.notice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 通知公告 员工查看
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Data
public class NoticeEmployeeVO extends NoticeVO {

    @ApiModelProperty("보기 또는 보기 안 함")
    private Boolean viewFlag;

    @ApiModelProperty("릴리스 날짜")
    private LocalDate publishDate;

}
